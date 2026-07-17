#!/bin/bash
set -euo pipefail

# ====================================================================
# [상수 설정] 사용자 환경에 맞게 수정해주세요.
# ====================================================================
SERVER_USER="gari"                       # 서버 접속 계정
SERVER_IP="garistyle.synology.me"        # 서버 IP 주소
SERVER_PASS="Kwan@0504!"                 # 서버 접속 비밀번호
TARGET_DIR="/volume1/web_packages/docker" # 서버에서 jar가 저장될 경로
JAR_NAME="megacoffee-0.0.1-SNAPSHOT.jar" # 빌드 후 생성되는 jar 파일명
LOCAL_PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
LOCAL_JAR_PATH="$LOCAL_PROJECT_DIR/target/$JAR_NAME"
REMOTE_JAR_PATH="$TARGET_DIR/$JAR_NAME"
# ====================================================================

echo "🚀 [1/4] 로컬에서 Spring Boot 프로젝트 빌드를 시작합니다..."
# 기존 빌드 파일 정리 후 새로 빌드 (테스트를 건너뛰려면 -x test 추가)
mvn clean package

# 빌드 결과 확인
if [ ! -f "$LOCAL_JAR_PATH" ]; then
    echo "❌ 빌드 결과물($LOCAL_JAR_PATH)이 없습니다."
    exit 1
fi

echo "✅ 빌드 성공!"

echo "📤 [2/4] 빌드된 JAR 파일을 서버로 전송합니다..."
sshpass -p "$SERVER_PASS" ssh -o StrictHostKeyChecking=no -o ConnectTimeout=10 "$SERVER_USER@$SERVER_IP" \
  "if [ ! -d '$TARGET_DIR' ]; then echo '❌ 원격 디렉터리가 없습니다: $TARGET_DIR'; exit 1; fi; if [ ! -w '$TARGET_DIR' ]; then echo '❌ 원격 디렉터리에 쓰기 권한이 없습니다: $TARGET_DIR'; exit 1; fi; echo '✅ 원격 디렉터리 확인 완료'"
sshpass -p "$SERVER_PASS" scp -o StrictHostKeyChecking=no -P 22 "$LOCAL_JAR_PATH" "$SERVER_USER@$SERVER_IP:$REMOTE_JAR_PATH"

echo "🔄 [3/4] 서버에서 기존 실행 중인 프로세스를 종료합니다..."
sshpass -p "$SERVER_PASS" ssh -o StrictHostKeyChecking=no "$SERVER_USER@$SERVER_IP" <<EOF
PID=\\$(pgrep -f "$JAR_NAME" || true)
if [ -n "\$PID" ]; then
    echo "💀 기존 프로세스(PID: \$PID)를 종료합니다."
    kill -15 \$PID
    sleep 3
else
    echo "ℹ️ 실행 중인 기존 프로세스가 없습니다."
fi
EOF

echo "🏃 [4/4] 서버에서 새 버전을 백그라운드로 실행합니다..."
sshpass -p "$SERVER_PASS" ssh -o StrictHostKeyChecking=no "$SERVER_USER@$SERVER_IP" <<EOF
cd "$TARGET_DIR"
nohup java -jar "$JAR_NAME" > nohup.out 2>&1 &
echo "🎉 서버 구동 명령 완료!"
EOF

echo "✨ 모든 배포 작업이 성공적으로 끝났습니다!"
