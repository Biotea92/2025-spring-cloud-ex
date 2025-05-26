package com.study.cloud.service.controller;

import com.study.cloud.service.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
@RefreshScope
public class SampleController {
    
    @Value("${message:기본 메시지}")
    private String message;
    
    private final SampleService sampleService;
    
    @Autowired
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }
    
    @GetMapping
    public String getMessage() {
        return "샘플 서비스 메시지: " + message;
    }
    
    @GetMapping("/data")
    public String getData() {
        return sampleService.getSampleData();
    }
}
