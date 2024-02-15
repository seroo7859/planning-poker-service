### Stage 1: Build the fat JAR and extract the layers ###
FROM openjdk:17-jdk-alpine as build
WORKDIR /workspace/app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN dos2unix ./mvnw
RUN chmod +x ./mvnw
RUN ./mvnw install -DskipTests
RUN java -Djarmode=layertools -jar target/*.jar extract --destination target/extracted

### Stage 2: Run the app ###
FROM openjdk:17-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
WORKDIR /app
ARG EXTRACTED=/workspace/app/target/extracted
COPY --from=build ${EXTRACTED}/dependencies ./
COPY --from=build ${EXTRACTED}/spring-boot-loader ./
COPY --from=build ${EXTRACTED}/snapshot-dependencies ./
COPY --from=build ${EXTRACTED}/application ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]