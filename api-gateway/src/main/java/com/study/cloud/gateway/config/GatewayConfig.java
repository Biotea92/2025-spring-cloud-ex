package com.study.cloud.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class GatewayConfig {

    /**
     * IP 주소 기반 키 리졸버
     * 클라이언트 IP 주소를 기준으로 요청 속도를 제한합니다.
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> {
            String xForwardedFor = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");

            String ip;
            if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
                ip = xForwardedFor.split(",")[0].trim();
            } else {
                ip = exchange.getRequest().getRemoteAddress().getHostString();
            }
            log.info("ipKeyResolver ip: {}", ip);

            return Mono.just(ip);
        };
    }
}
