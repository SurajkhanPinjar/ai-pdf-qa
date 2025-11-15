# ================================================
# 🏗️ Stage 1: Build the application using Gradle
# ================================================
FROM gradle:8.5-jdk17-jammy AS builder

WORKDIR /app

# Copy Gradle configuration files
COPY build.gradle settings.gradle ./
COPY gradle gradle

# Download dependencies (cache layer)
RUN gradle build --no-daemon || true

# Copy the source code
COPY src src

# Build the application JAR
RUN gradle clean bootJar --no-daemon


# ================================================
# 🚀 Stage 2: Run the application
# ================================================
FROM eclipse-temurin:17-jre AS runner

WORKDIR /app

# 🔥🔥 Enable Java Remote Debugging on port 5005
ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]