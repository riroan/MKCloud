## 백엔드

### 실행

```
./gradlew build
docker-compose up
```

### 필요한 파일

```
# mysql.env

MYSQL_ROOT_PASSWORD: mysql 기본 루트 비밀번호
```

```
# backend.env
BOX_PATH: 업로드된 파일이 저장될 위치
TOKEN_SECRET: 비밀번호 암호화 키
DB_USERNAME: 데이터베이스 접속 아이디
DB_PASSWORD: 데이터베이스 접속 패스워드
EXPIRE_LENGTH: JWT 만료 기간
MAX_FILE_SIZE: 각 파일마다 보낼 수 있는 최대 용량
MAX_REQUEST_SIZE: 한번의 요청으로 보낼 수 있는 전체 파일 용량
PORT: 서버를 실행할 포트
SECRET_ALGORITHM: 비밀번호 암호화 알고리즘
```

