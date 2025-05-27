# Redis Docker 설정

이 디렉토리에는 [Redis](https://redis.io/) 인메모리 데이터 저장소를 Docker로 실행하기 위한 설정 파일이 포함되어 있습니다.

## 사용 방법

### 단일 Docker 명령으로 실행

공식 이미지를 직접 사용할 경우:

```bash
docker run -d -p 6379:6379 --name redis redis:latest
```

### Docker Compose로 실행

이 디렉토리에 포함된 docker-compose.yml 파일을 사용하여 실행:

```bash
docker-compose up -d
```

## 접속 방법

Redis CLI를 사용하여 접속:

```bash
docker exec -it redis redis-cli
```

## API Gateway 연동

API Gateway는 Config Server를 통해 Redis 설정을 자동으로 가져옵니다.
Config Server의 api-gateway.yml 파일에 다음 설정이 포함되어 있습니다:

```yaml
spring:
  redis:
    host: localhost
    port: 6379
```

이 설정은 API Gateway에 적용되며, 속도 제한(Rate Limiting) 기능을 위해 Redis를 사용합니다.
