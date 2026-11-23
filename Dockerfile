# Runtime image only: the jar is built by Maven first (locally or in CI),
# so dependency downloads never happen inside Docker.
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY target/two-num-*.jar app.jar

# Run as a non-root user
RUN addgroup -S app && adduser -S app -G app
USER app

# Cloud Run / Container Apps pass the port in $PORT; App.java reads it (default 8080)
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
