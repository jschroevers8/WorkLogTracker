FROM gradle:8.8-jdk21 as builder
WORKDIR /app
COPY . .
RUN gradle shadowJar

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/backend-all.jar /app/backend.jar
EXPOSE 8080
CMD ["java", "-jar", "backend.jar"]

