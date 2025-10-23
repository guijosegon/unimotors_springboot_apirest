# UniMotors - API (scaffold)

Este repositório contém o scaffold inicial para a API UniMotors (Spring Boot 3, JWT, JPA, Flyway, PostgreSQL).

## Estrutura inicial

- `pom.xml` - dependências e build Maven
- `src/main/java/br/com/unimotors/UniMotorsApplication.java` - classe principal
- `src/main/resources/application.properties` - configuração básica (variáveis via env)
- `src/main/resources/db/migration/V1__init.sql` - migration inicial (schema)

## Requisitos locais
- Java 17+
- Maven 3.8+
- PostgreSQL 12+

## Como criar o repositório no GitHub e subir o código
1. Inicialize git e faça o commit localmente:

   git init
   git add .
   git commit -m "Scaffold inicial UniMotors"

2. Crie um repositório no GitHub (via web) chamado `apirest_springboot_unimotors`.
3. Adicione o remoto e envie:

   git remote add origin https://github.com/<seu-usuario>/apirest_springboot_unimotors.git
   git branch -M main
   git push -u origin main

## Variáveis de ambiente (exemplos)
- DB_HOST (padrão: localhost)
- DB_PORT (padrão: 5432)
- DB_NAME (padrão: unimotors)
- DB_USER (padrão: postgres)
- DB_PASSWORD (padrão: postgres)
- APP_JWT_SEGREDO (não deixe default em produção)

## Rodando localmente
- Criar banco de dados no PostgreSQL:

   CREATE DATABASE unimotors;

- Rodar via Maven:

   mvn -q spring-boot:run

Flyway irá aplicar `V1__init.sql` automaticamente na primeira execução.

## Próximos passos sugeridos
- Implementar módulos: `autenticacao`, `usuario`, `catalogo`, `anuncio`, `negocio`.
- Adicionar DTOs, controllers, serviços e repositórios.
- Configurar segurança JWT e testes.

