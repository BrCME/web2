FROM openjdk:21 AS BUILD

LABEL author="BrCME"

WORKDIR /app

VOLUME /tmp

COPY .mnv mvnw src app/

CMD ["./mvnw", "clean", "package"]

FROM BUILD AS RUN

EXPOSE 8443 8080

CMD ["java", "-jar", "./target/safia-0.0.1-SNAPSHOT.jar"]
