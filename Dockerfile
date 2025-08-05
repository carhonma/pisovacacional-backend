# ----------- Etapa de build -----------
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .

# Hacemos ejecutable mvnw y compilamos el proyecto sin tests
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# ----------- Etapa de ejecución -----------
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

