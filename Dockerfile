# Etapa 1: build da aplicação
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia arquivos de projeto
COPY pom.xml ./
COPY src ./src

# Compila o projeto (modo JVM padrão)
RUN mvn clean package -DskipTests

# Etapa 2: imagem final para execução
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=build /app/target/*-runner.jar app.jar

# Expondo porta padrão do Quarkus
EXPOSE 8080

# Define o ponto de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]
