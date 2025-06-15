FROM maven:3.9.7-eclipse-temurin-21-alpine AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn -q -B dependency:go-offline

COPY src ./src
RUN mvn -q -B package -DskipTests

# ----------------------------------------------------------------

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

VOLUME /tmp

ENV JAVA_OPTS=""

COPY --from=builder /app/target/mazy-video-tools-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
