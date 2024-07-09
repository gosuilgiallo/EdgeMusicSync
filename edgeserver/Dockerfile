FROM openjdk:17-jdk-slim
WORKDIR /app
COPY EdgeServer.java .
RUN javac EdgeServer.java
CMD ["java", "EdgeServer"]