FROM eclipse-temurin:21-jre-alpine

COPY jars/app.jar app.jar

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
