# Spring Cloud 멀티모듈 데모 프로젝트

이 프로젝트는 Spring Cloud의 주요 컴포넌트를 학습하기 위한 멀티모듈 데모 프로젝트입니다.

## 프로젝트 구조

이 프로젝트는 다음과 같은 모듈로 구성되어 있습니다:

- **config-server**: 중앙 집중식 설정 관리 서버 (포트: 8888)
- **eureka-server**: 서비스 디스커버리 서버 (포트: 8761)
- **api-gateway**: API 게이트웨이 (포트: 8090)
- **sample-service**: 샘플 마이크로서비스 - 상품 정보 제공 (포트: 8080)
- **order-service**: 주문 마이크로서비스 - 주문 관리 및 상품 서비스 호출 (포트: 8081)

## 실행 방법

각 모듈은 다음 순서대로 실행해야 합니다:

1. **Config Server 실행**
   ```
   ./gradlew :config-server:bootRun
   ```

2. **Eureka Server 실행**
   ```
   ./gradlew :eureka-server:bootRun
   ```

3. **Sample Service 실행**
   ```
   ./gradlew :sample-service:bootRun
   ```

4. **Order Service 실행**
   ```
   ./gradlew :order-service:bootRun
   ```

5. **API Gateway 실행**
   ```
   ./gradlew :api-gateway:bootRun
   ```

## 접속 주소

- Config Server: http://localhost:8888
- Eureka 대시보드: http://localhost:8761
- 샘플 서비스(직접 접근): http://localhost:8080/sample
- 주문 서비스(직접 접근): http://localhost:8081/orders
- API Gateway를 통한 접근:
  - 샘플 서비스: http://localhost:8090/api/sample
  - 주문 서비스: http://localhost:8090/api/orders

## 마이크로서비스 통신 테스트

다음 엔드포인트를 통해 마이크로서비스 간 통신을 테스트할 수 있습니다:

1. 모든 주문 조회: 
   ```
   GET http://localhost:8090/api/orders
   ```

2. 상품 정보가 포함된 주문 조회:
   ```
   GET http://localhost:8090/api/orders/with-product-info/1
   ```
   이 요청은 order-service가 sample-service를 호출하여 상품 상세 정보를 가져오는 것을 보여줍니다.

## Spring Cloud 주요 기능

1. **설정 관리 (Config Server)**
   - 중앙 집중식 설정 관리
   - 환경별 설정 제공 (dev, prod)

2. **서비스 디스커버리 (Eureka)**
   - 서비스 등록 및 조회
   - 상태 모니터링

3. **API 게이트웨이**
   - 라우팅
   - 필터링
   - 로드 밸런싱

4. **서비스 간 통신 (Feign Client)**
   - 선언적 REST 클라이언트
   - 서비스 디스커버리 통합

5. **회복성 패턴 (Circuit Breaker)**
   - 서킷 브레이커
   - 폴백 메소드
   - 재시도

## 다음 단계로 학습할 수 있는 Spring Cloud 기능

- Spring Cloud Stream (메시징)
- Spring Cloud Sleuth & Zipkin (분산 추적)
- Spring Cloud Security (보안)
