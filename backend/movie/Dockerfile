FROM eclipse-temurin:21-jre-jammy

COPY jars/app.jar app.jar

ENV JAVA_OPTS=""
ENV KEYSTORE_PATH=/app/keystores/keystore.p12

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
