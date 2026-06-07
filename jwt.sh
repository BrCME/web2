#! /bin/bash

# Gerando os certificados CA
ssh-keygen -f ./src/main/resources/ca_user_key -t rsa -b 4096 -P ""
ssh-keygen -f ./src/main/resources/ca_host_key -t rsa -b 4096 -P ""

# Certificando as chaves CA geradas
ssh-keygen -s ./src/main/resources/ca_host_key -I safia -h ./src/main/resources/ca_host_key.pub

# Gerando novas chaves RSA/ECDSA
ssh-keygen -f ./src/main/resources/public_rsa -t rsa -b 4096 -P ""

