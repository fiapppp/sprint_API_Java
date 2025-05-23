# Etapa 1: build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia tudo para garantir que o contexto esteja correto
COPY . .

RUN mvn clean package -DskipTests

# Etapa 2: imagem final
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY --from=build /app/target/*-runner.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]