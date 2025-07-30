# Primeira tentativa de fazer um projeto SpringBoot rodar via docker

FROM openjdk:21
VOLUME /tmp
COPY . /usr/src/sofia
WORKDIR /usr/src/sofia
# RUN ./mvnw test
CMD ["./mvnw", "clean", "spring-boot:build"]
