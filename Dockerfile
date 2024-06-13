FROM maven:3.9.6-eclipse-temurin-22 as dev-build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src /build/src
RUN ["mvn","clean","package","-DskipTests"]

FROM openjdk:22-jdk-oracle
COPY --from=dev-build /build/target/*.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
CMD java -jar app.jar