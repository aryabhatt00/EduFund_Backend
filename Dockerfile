# Use Java 21 base image
FROM eclipse-temurin:21-jdk-alpine

# Install Maven
RUN apk add --no-cache maven

# Set working directory
WORKDIR /app

# Copy entire project source code
COPY . .

# Build the application using Maven (skip tests to speed up)
RUN mvn clean package -DskipTests

# Expose the port the app runs on
EXPOSE 8080

# Run the JAR file (the name must match what Maven builds)
CMD ["java", "-jar", "target/bankingNew-0.0.1-SNAPSHOT.jar"]
