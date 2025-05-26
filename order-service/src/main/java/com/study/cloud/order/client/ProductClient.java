package com.study.cloud.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sample-service")
public interface ProductClient {
    
    @GetMapping("/sample/products/{id}")
    String getProductInfo(@PathVariable("id") Long id);
}
