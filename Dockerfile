# Multi-stage Dockerfile: build with Gradle wrapper, produce a small runtime image

# --- Build stage -----------------------------------------------------------
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /workspace

# Copy Gradle wrapper and build files first to leverage Docker layer cache
COPY gradlew gradlew
COPY gradle gradle
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

COPY build.gradle settings.gradle ./

# Download dependencies (cached layer — only re-runs when build files change)
RUN ./gradlew dependencies --no-daemon

# Copy source and build (skip tests — run them in CI, not here)
COPY src src
RUN ./gradlew -x test bootJar --no-daemon


# --- Runtime stage ---------------------------------------------------------
FROM eclipse-temurin:21-jre-jammy

LABEL maintainer="team@example.com"
ARG JAR_FILE=build/libs/worldweather-0.0.1-SNAPSHOT.jar
# Allow operators to tune memory at container runtime via JAVA_OPTS
ENV JAVA_OPTS=""

# Expose the default Spring Boot port
EXPOSE 8080
VOLUME /tmp

# Create a non-root user for improved security
RUN groupadd -r app && useradd -r -g app app

# Create log directory expected by application.yml
RUN mkdir -p /opt/app/logs

# Install wget so healthchecks work in the runtime image
RUN apt-get update && apt-get install -y wget --no-install-recommends && rm -rf /var/lib/apt/lists/*

# Copy the jar built in the previous stage into the runtime image
COPY --from=build /workspace/${JAR_FILE} /opt/app/app.jar
RUN chown -R app:app /opt/app
USER app
WORKDIR /opt/app

HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run the jar using exec form so signals are forwarded properly
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /opt/app/app.jar"]
