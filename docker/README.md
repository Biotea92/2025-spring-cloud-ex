# Docker 설정

이 디렉토리에는 Spring Cloud 데모 프로젝트에 필요한 인프라 서비스를 Docker로 실행하기 위한 설정 파일이 포함되어 있습니다.

## 포함된 서비스

- **Zipkin**: 분산 추적 시스템 (포트: 9411)
- **Redis**: 인메모리 데이터 저장소, API Gateway 속도 제한에 사용 (포트: 6379)

## 사용 방법

### 모든 서비스 한 번에 실행

루트 docker 디렉토리에서 다음 명령을 실행하여 모든 서비스를 시작할 수 있습니다:

```bash
docker-compose up -d
```

### 개별 서비스 실행

각 서비스는 해당 서브디렉토리에서 개별적으로 실행할 수도 있습니다:

- Zipkin 실행:
  ```bash
  cd zipkin
  docker-compose up -d
  ```

- Redis 실행:
  ```bash
  cd redis
  docker-compose up -d
  ```

## 서비스 접속 방법

- **Zipkin UI**: http://localhost:9411
- **Redis CLI**: `docker exec -it redis redis-cli`

## 서비스 중지

모든 서비스 중지:

```bash
docker-compose down
```

## 마이크로서비스 연동

각 서비스의 README.md 파일에서 마이크로서비스와의 연동 방법에 대한 자세한 정보를 확인할 수 있습니다:

- [Zipkin 연동 방법](./zipkin/README.md)
- [Redis 연동 방법](./redis/README.md)
