FROM openjdk:11-jdk
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]
