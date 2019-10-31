FROM lwieske/java-8

ARG JAR_FILE

COPY ${JAR_FILE} /opt/app.jar

ENTRYPOINT ["java", "-Dmode=local", "/opt/app.jar"]

EXPOSE 8080