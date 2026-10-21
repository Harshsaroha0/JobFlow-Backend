FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY . .
RUN mvn -f jobflow-monolith/pom.xml -DskipTests -Dmaven.test.skip=true clean package

FROM eclipse-temurin:21-jre
WORKDIR /app
RUN mkdir -p /data/resumes
COPY --from=build /workspace/jobflow-monolith/target/jobflow-monolith-1.0.0.jar /app/jobflow.jar
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=70.0", "-jar", "/app/jobflow.jar"]