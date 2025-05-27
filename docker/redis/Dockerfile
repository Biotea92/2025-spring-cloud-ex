# Redis 서버를 위한 Dockerfile
FROM redis:latest

# 메타데이터 추가
LABEL maintainer="example@example.com"
LABEL description="Redis for API Gateway rate limiting"
LABEL version="1.0"

# 포트 노출
EXPOSE 6379

# 헬스체크 설정
HEALTHCHECK --interval=5s --timeout=3s --retries=3 \
  CMD redis-cli ping || exit 1

# 볼륨 설정 (선택 사항: 데이터 지속성을 위해)
VOLUME ["/data"]

# Redis 설정 (필요에 따라 조정)
CMD ["redis-server", "--appendonly", "yes"]
