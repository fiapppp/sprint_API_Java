# Etapa 1: build da aplicação
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build

# Copia o conteúdo do projeto para dentro do container
COPY . .

# Compila o projeto e gera o jar final (sem testes)
RUN mvn clean package -DskipTests

# Etapa 2: imagem final para rodar a aplicação
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copia o jar gerado na etapa anterior
COPY --from=build /build/target/*-runner.jar app.jar

# Expõe a porta padrão da aplicação
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]