FROM eclipse-temurin:21-jdk-alpine AS builder
ARG APP_HOME=/opt/app
WORKDIR ${APP_HOME}
RUN --mount=type=cache,target=/root/.gradle \
    apk add --no-cache bash
COPY gradle/ gradle
COPY gradlew build.gradle settings.gradle .
COPY ./src ./src
RUN ./gradlew clean bootJar

FROM eclipse-temurin:21-jre-alpine
ARG APP_HOME=/opt/app
WORKDIR ${APP_HOME}
EXPOSE 8080
COPY --from=builder ${APP_HOME}/build/libs/*.jar ${APP_HOME}/app.jar
ENTRYPOINT ["java","-jar","app.jar"]