# web2

Trabalho Final da disciplina de Programação Web II. Incrementado com isolamento entre API e Clientes, usando a arquitetura de .

## Descrição

Com o **Sistema SAFIA** (Sistema de Apoio e Facilitação de Integração de Atividades), a comunicação entre os membros da equipe se torna centralizada, o gesto consegue acompanhar em tempo real o andamento das atividades e os riscos de atraso são reduzidos, promovento maior eficiência no desenvolvimento do projeto.

## Requisitos

- [x] CRUD para Usuário
- [x] CRUD para Equipe
- [x] CRUD para Tarefa
- [x] CRUD para Projeto
- [x] CRUD para Trabalho
- [x] Autenticação e Autorização
- [x] Logging
- [x] Telemetria
- [x] Docker
- [x] Testes Unitários
- [x] Cache
- [x] Migrations
- [x] Geração de Relatórios

## Recursos Disponíveis

Este projeto disponibiliza fluxos de trabalho condicionados ao usuário logado:

- Usuario Administrador:
	- Pode ativar/desativar um (ou mais) usuario
	- Pode ativar/desativar um (ou mais) time
	- Pode gerar relatórios de dados por usuario
	- Pode gerar relatórios de dados por time
- Usuário Empregado:
	- Pode cadastrar um (ou mais) time em sua posse
	- Pode alterar os dados de um time em sua posse
	- Pode ativar/desativar um (ou mais) projeto em sua posse
	- Pode alterar os dados de um projeto em sua posse
- Usuário Analista:
	- Pode gerar relatórios de dados por usuario
	- Pode gerar relatórios de dados por time

## Arquitetura

A arquitetura da API SAFIA está disposta utilizando SpringBoot Modular Monolith. Dessa forma, há o desacoplamento lógico entre módulos, como em microsserviços, enquanto o código fonte reside em um único projeto, como em monolito.

Dessa forma, cada módulo mantém seus dados em um schema separado de um database centralizado, aproveitando somente a estrutura de dados útil para o próprio funcionamento.

Detalhes de implementação e/ou abstração podem ser conferidos no arquivo `sketch.excalidraw`.

<!-- ## Instalação -->

<!-- Para instalar o projeto é necessário  -->

## Inicialização

Para iniciar o projeto, será necessário os seguintes comandos:

```sh
# Executando via Maven Wrapper
./mvnw spring-boot:run
```

## Testes

Para realizar os testes, será necessário os seguintes comandos:

```sh
# Executando via Maven Wrapper
./mvnw test
```

## Tecnologias

Para a realização deste projeto, foram utilizadas as seguintes tecnologias:

- Maven
- Java
- SpringBoot
- LOG4J2
- JPA com Hibernate
- Flyway
- PostgreSQL
- Dbeaver
- JasperSoft Studio ou IText
- JUnit5
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
