# Multi-stage Dockerfile: build with Maven, produce a small runtime image

# --- Build stage -----------------------------------------------------------
FROM maven:3.6.3-jdk-8 AS build
WORKDIR /workspace

# Copy pom first to leverage Docker layer cache for dependencies
COPY pom.xml ./
# Copy source (copy whole src directory)
COPY src ./src

# Run Maven package (skip tests) to produce the fat jar in target/
RUN mvn -B -DskipTests package


# --- Runtime stage ---------------------------------------------------------
# NOTE: Using the maven base image as runtime is larger but guarantees the image
# can be built in environments where lightweight OpenJDK runtime tags can't be
# resolved. Replace the following line with a small runtime (for example
# `openjdk:8-jre-slim-buster` or an Eclipse Temurin JRE) when Docker Hub access
# allows.
FROM maven:3.6.3-jdk-8

# The jar name produced by the Maven build; keep as ARG so it can be overridden
ARG JAR_NAME=worldweather-0.0.1-SNAPSHOT.jar
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Expose the default Spring Boot port
EXPOSE 8080
VOLUME /tmp

# Copy the jar built in the previous stage into the runtime image
COPY --from=build /workspace/target/${JAR_NAME} /app.jar

# Run the jar, allowing additional JAVA_OPTS to be passed at runtime
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app.jar"]
