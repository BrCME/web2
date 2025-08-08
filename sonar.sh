#!/bin/bash

# Subindo o container com o SonarQube
docker compose up -d

# Rodando o verificador do Sonar para o projeto SAF.IA em localhost
mvn clean verify sonar:sonar -Dsonar.projectKey=SAF.IA -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_2ed9d2e9630fd3aa02f8ced8881495c2e9cc7916
