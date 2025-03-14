# Étape de build
FROM maven:3.8.8-openjdk-21 AS build  # Utilise une version Maven compatible
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline  # Cache les dépendances Maven
COPY src ./src
RUN mvn clean package -DskipTests  # Build optimisé

# Étape de runtime
FROM amazoncorretto:21
ARG PROFILE=dev
ARG APP_VERSION=1.0.0

WORKDIR /app
COPY --from=build /build/target/productManagement-*.jar /app/productManagement.jar

EXPOSE 8080
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}

CMD ["java", "-Xmx1024M", "-jar", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "productManagement.jar"]
