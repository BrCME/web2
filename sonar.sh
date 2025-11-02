#!/bin/bash

# Subindo o container com o SonarQube
docker compose up -d

# Rodando o verificador do Sonar para o projeto SAF.IA em localhost
mvn clean verify sonar:sonar -Dsonar.projectKey=safia -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_6b758cd8d7324534a13ff8957425d586a4b6a679
