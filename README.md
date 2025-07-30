# web2

Trabalho Final da disciplina de Programação Web II.

## Descrição

O SAF.IA é um software de agronegócio voltado ao público rural de média e pequena escala, para auxiliar com verificação das condições de solo, verificação do clima local, recomendação de plantação, além de um mercado interno para conectar o campo aos produtos que o agricultor precisa.

## Requisitos

- CRUD para Usuário
- CRUD para Agricultor
- CRUD para Produto
- CRUD para Empresa
- Painél Administrativo
- Autenticação e Autorização
- Coleta de dados de Clima via API
- Armazenamento de dados de Clima
- Cache
- Migrations
- MPA ou SPA
- Geração de Relatórios

## Recursos Disponíveis

Este projeto disponibiliza fluxos de trabalho condicionados ao usuário logado:

- Usuario Administrador:
	- Pode ativar/desativar um (ou mais) agricultor
	- Pode ativar/desativar um (ou mais) vendedor
	- Pode gerar relatórios de dados por agricultor
	- Pode gerar relatórios de dados por vendedor
- Usuário Agricultor:
	- Pode cadastrar uma (ou mais) propriedade rural em sua posse
	- Pode alterar os dados de uma propriedade em sua posse
	- Pode ativar/desativar uma (ou mais) propriedade rural em sua posse
	- Pode consultar as informações de clima e tempo de uma propriedade em sua posse
- Usuário Vendedor:
	- Pode cadastrar um produto comercializável em sua posse
	- Pode alterar os dados de um produto comercializável em sua posse
	- Pode ativar/desativar um (ou mais) produto comercializável em sua posse

## Instalação

Para instalar o projeto é necessário 

## Inicialização

Para iniciar o projeto, será necessário os seguintes comandos:

```sh
# Executando via Maven
./mvnw run:spring-boot

# Executando via Gradle
./gradlew bootRun
```

## Testes

Para realizar os testes, será necessário os seguintes comandos:

```sh
# Executando via Maven
./mvnw test:spring-boot

# Executando via Gradle
./gradlew test
```

## Tecnologias

Para a realização deste projeto, foram utilizadas as seguintes tecnologias:

- TailWind
- Thymeleaf
- HTMX
- JavaScript
- Maven ou Gradle
- Java
- SpringBoot
- LOG4J2
- JPA com Hibernate
- Flyway
- PostgreSQL
- Dbeaver
- JasperSoft Studio ou IText
- Docker
- Docker Compose

## Contribuidores

<table>
	<tbody>
		<td>
			<img src="./assets/brunao.png" width="200" border="2" style="border-radius: 50%;" />
			<p style="text-align: center;"><a href="https://github.com/Magr0g" target="_blank">Bruno Meireles</a></p>
		</td>
		<td>
			<img src="./assets/pedro.png" width="200" border="2" style="border-radius: 50%;" />
			<p style="text-align: center;"><a href="https://github.com/rabispedro" target="_blank">Pedro Rabis</a></p>
		</td>
	</tbody>
</table>
