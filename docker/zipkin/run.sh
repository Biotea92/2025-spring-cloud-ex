#!/bin/bash

# Zipkin Docker 컨테이너 실행 스크립트

# 이전에 실행 중인 컨테이너가 있으면 중지하고 제거
echo "이전 Zipkin 컨테이너 확인 및 제거..."
if [ "$(docker ps -q -f name=zipkin)" ]; then
    docker stop zipkin
    docker rm zipkin
fi

# Zipkin Docker 컨테이너 실행
echo "Zipkin 컨테이너 시작..."
docker run -d --name zipkin \
  -p 9411:9411 \
  -e STORAGE_TYPE=mem \
  -e JAVA_OPTS="-Xms512m -Xmx512m" \
  --restart unless-stopped \
  openzipkin/zipkin

echo "Zipkin이 실행되었습니다. http://localhost:9411 에서 UI에 접속할 수 있습니다."
