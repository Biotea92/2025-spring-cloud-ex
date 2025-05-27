# Zipkin Docker 설정

이 디렉토리에는 [Zipkin](https://zipkin.io/) 분산 추적 시스템을 Docker로 실행하기 위한 설정 파일이 포함되어 있습니다.

## 사용 방법

### 단일 Docker 명령으로 실행

공식 이미지를 직접 사용할 경우:

```bash
docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin
```

### Docker Compose로 실행

이 디렉토리에 포함된 docker-compose.yml 파일을 사용하여 실행:

```bash
docker-compose up -d
```


## 접속 방법

Zipkin UI는 다음 URL로 접속할 수 있습니다:

```
http://localhost:9411
```

## 마이크로서비스 연동

모든 마이크로서비스는 Config Server를 통해 Zipkin 설정을 자동으로 가져옵니다. 
Config Server의 application.yml 파일에 다음 설정이 포함되어 있습니다:

```yaml
management:
  tracing:
    sampling:
      probability: 1.0
    propagation:
      type: w3c
  zipkin:
    tracing:
      endpoint: http://localhost:9411/api/v2/spans
```

이 설정은 모든 마이크로서비스에 적용되며, 각 서비스는 Zipkin 서버로 추적 데이터를 전송합니다.
