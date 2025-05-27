package com.study.cloud.order.service;

import com.study.cloud.order.client.ProductClient;
import com.study.cloud.order.model.Order;
import com.study.cloud.order.model.OrderItem;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private static final String ORDER_SERVICE = "orderService";

    private final Map<Long, Order> orders = new HashMap<>();
    private final AtomicLong orderIdGenerator = new AtomicLong(1L);
    private final AtomicLong itemIdGenerator = new AtomicLong(1L);

    private final ProductClient productClient;

    @Autowired
    public OrderService(ProductClient productClient) {
        this.productClient = productClient;

        // 초기 데이터 생성
        createSampleOrders();
    }

    public Order createOrder(Order order) {
        order.setId(orderIdGenerator.getAndIncrement());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("CREATED");

        // 각 주문 아이템에 ID 할당
        order.getItems().forEach(item -> {
            item.setId(itemIdGenerator.getAndIncrement());
        });

        // 총 금액 계산
        double total = order.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        order.setTotalAmount(total);

        orders.put(order.getId(), order);
        logger.info("주문 생성: {}", order.getId());
        return order;
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    public Order getOrderById(Long id) {
        return orders.get(id);
    }

    @CircuitBreaker(name = ORDER_SERVICE, fallbackMethod = "getOrderWithProductInfoFallback")
    @Bulkhead(name = ORDER_SERVICE, fallbackMethod = "getOrderWithProductInfoFallback")
    public Order getOrderWithProductInfo(Long id) {
        Order order = orders.get(id);
        if (order != null) {
            for (OrderItem item : order.getItems()) {
                try {
                    // ProductClient를 통해 상품 정보 호출
                    String productInfo = productClient.getProductInfo(item.getProductId());
                    // 상품 정보 설정
                    item.setProductDescription(productInfo);
                } catch (Exception e) {
                    logger.error("상품 정보 조회 실패: {}", e.getMessage());
                    throw e;
                }
            }
        }
        return order;
    }

    public Order getOrderWithProductInfoFallback(Long id, Exception ex) {
        logger.warn("상품 정보 호출 실패로 폴백 메소드 실행: {}", ex.getMessage());
        Order order = orders.get(id);
        if (order != null) {
            for (OrderItem item : order.getItems()) {
                item.setProductDescription("[일시적 정보 조회 불가] - 기본 상품 설명");
            }
        }
        return order;
    }

    // 샘플 주문 데이터 생성
    private void createSampleOrders() {
        Order order1 = new Order();
        order1.setId(orderIdGenerator.getAndIncrement());
        order1.setCustomerName("홍길동");
        order1.setOrderDate(LocalDateTime.now().minusDays(1));
        order1.setStatus("COMPLETED");

        OrderItem item1 = new OrderItem();
        item1.setId(itemIdGenerator.getAndIncrement());
        item1.setProductId(1L);
        item1.setProductName("노트북");
        item1.setQuantity(1);
        item1.setPrice(1200000.0);

        OrderItem item2 = new OrderItem();
        item2.setId(itemIdGenerator.getAndIncrement());
        item2.setProductId(2L);
        item2.setProductName("마우스");
        item2.setQuantity(1);
        item2.setPrice(35000.0);

        order1.getItems().add(item1);
        order1.getItems().add(item2);
        order1.setTotalAmount(1235000.0);

        orders.put(order1.getId(), order1);

        Order order2 = new Order();
        order2.setId(orderIdGenerator.getAndIncrement());
        order2.setCustomerName("김철수");
        order2.setOrderDate(LocalDateTime.now().minusHours(3));
        order2.setStatus("PROCESSING");

        OrderItem item3 = new OrderItem();
        item3.setId(itemIdGenerator.getAndIncrement());
        item3.setProductId(3L);
        item3.setProductName("키보드");
        item3.setQuantity(1);
        item3.setPrice(85000.0);

        order2.getItems().add(item3);
        order2.setTotalAmount(85000.0);

        orders.put(order2.getId(), order2);
    }
}
