# Zipkin 서버를 위한 Dockerfile
FROM openzipkin/zipkin:latest

# 메타데이터 추가
LABEL maintainer="example@example.com"
LABEL description="Zipkin distributed tracing system"
LABEL version="1.0"

# 포트 노출
EXPOSE 9411

# 헬스체크 설정
HEALTHCHECK --interval=5s --timeout=3s --retries=3 \
  CMD wget -q -O- http://localhost:9411/health || exit 1

# 볼륨 설정 (선택 사항: 데이터 지속성을 위해)
VOLUME ["/zipkin"]

# 환경 변수 설정 예시 (필요에 따라 조정)
ENV STORAGE_TYPE=mem
ENV JAVA_OPTS="-Xms512m -Xmx512m"

# 컨테이너 시작 시 실행될 명령
# openzipkin/zipkin 이미지는 이미 ENTRYPOINT가 설정되어 있으므로
# 추가 CMD는 필요하지 않음
