# Compilação pesada com JDK 25 e Maven Wrapper
FROM eclipse-temurin:25-jdk-alpine AS builder

WORKDIR /app

# Copia o wrapper e o pom.xml primeiro (otimização de cache)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Garante que o wrapper tem permissão de execução
RUN chmod +x ./mvnw

# Baixa as dependências offline usando o wrapper
RUN ./mvnw dependency:go-offline

# Copia o código-fonte
COPY src ./src

# Compila o projeto gerando o .jar
RUN ./mvnw clean package -DskipTests

# Imagem leve apenas com o JRE 25)
FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

# Copia apenas o .jar
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 1234

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/ludoteca
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgres

ENTRYPOINT ["java", "-jar", "app.jar"]