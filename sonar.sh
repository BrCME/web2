#!/bin/bash

# Subindo o container com o SonarQube
docker compose up -d

# Rodando o verificador do Sonar para o projeto SOF.IA em localhost
mvn clean verify sonar:sonar -Dsonar.projectKey=SOF.IA -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_67c73551a443a9fa2d849c51999d47339786f686
