FROM openjdk:17-jdk-slim
WORKDIR /app
COPY CentralServer.java .
RUN javac CentralServer.java
CMD ["java", "CentralServer"]