# Primeira tentativa de fazer um projeto SpringBoot rodar via docker

FROM openjdk:21

VOLUME /tmp

COPY . /usr/src/safia

WORKDIR /usr/src/safia

# RUN ./mvnw test

CMD ["./mvnw", "clean", "spring-boot:build"]
