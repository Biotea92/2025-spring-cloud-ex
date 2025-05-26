package com.study.cloud.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sample/products")
public class ProductController {

    private final Map<Long, String> productDescriptions = new HashMap<>();

    public ProductController() {
        // 샘플 상품 설명 초기화
        productDescriptions.put(1L, "최신 Intel CPU와 16GB RAM을 탑재한 고성능 노트북입니다.");
        productDescriptions.put(2L, "무선 광학 마우스, 인체공학적 디자인으로 편안한 사용감을 제공합니다.");
        productDescriptions.put(3L, "기계식 키보드, 청축 스위치로 타이핑시 경쾌한 타격감이 특징입니다.");
        productDescriptions.put(4L, "27인치 4K 모니터, 넓은 색영역과 높은 해상도로 선명한 화질을 제공합니다.");
        productDescriptions.put(5L, "블루투스 5.0 지원 무선 헤드폰, 노이즈 캔슬링 기능이 탑재되어 있습니다.");
    }

    @GetMapping("/{id}")
    public String getProductInfo(@PathVariable Long id) {
        // 랜덤으로 지연 또는 에러를 발생시켜 서킷 브레이커 테스트 가능하게 함
        if (Math.random() > 0.8) {
            try {
                Thread.sleep(3000); // 3초 지연
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (Math.random() > 0.9) {
            throw new RuntimeException("상품 정보 서비스 일시적 장애 발생");
        }

        String description = productDescriptions.getOrDefault(id, "상품 정보가 없습니다.");
        return description;
    }
}
