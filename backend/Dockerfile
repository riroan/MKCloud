FROM openjdk:17-jdk

ARG JAR_FILE=./build/libs/backend-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV TOKEN_SECRET=${TOKEN_SECRET}
ENV EXPIRE_LENGTH=${EXPIRE_LENGTH}
ENV MAX_FILE_SIZE=${MAX_FILE_SIZE}
ENV MAX_REQUEST_SIZE=${MAX_REQUEST_SIZE}
ENV SECRET_ALGORITHM=${SECRET_ALGORITHM}

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
