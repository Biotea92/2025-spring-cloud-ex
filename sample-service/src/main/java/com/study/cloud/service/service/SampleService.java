package com.study.cloud.service.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class SampleService {
    
    private static final String SAMPLE_SERVICE = "sampleService";
    
    @CircuitBreaker(name = SAMPLE_SERVICE, fallbackMethod = "getDefaultData")
    public String getSampleData() {
        // 실제로는 여기에 외부 서비스 호출 등의 코드가 있을 수 있음
        // 예시를 위해 가끔 예외를 발생시키도록 설정
        if (Math.random() > 0.7) {
            throw new RuntimeException("서비스 일시적 장애 발생");
        }
        return "샘플 서비스의 실제 데이터";
    }
    
    public String getDefaultData(Exception e) {
        return "대체 응답 (폴백): " + e.getMessage();
    }
}
