# Requisitos do Sistema – NOME_DO_SISTEMA

> Trabalho 1 – Desenvolvimento Backend com Spring Boot  
> Curso: Desenvolvimento Backend com Spring  
> Equipe: Guilherme José, Allan e Dionathan Tomaz  

---

## 1. Objetivo do Sistema

Desenvolver uma **API REST completa** inspirada em um grande case real (por exemplo: Netflix, Spotify, MercadoLivre, Steam, iFood, jogo online, sistema de agendamento, etc.), porém com **nome e identidade próprios** (ex.: UnescFlix, SoundHub, UniMarket, JogaUnesc, etc.).

O sistema deverá:

- Implementar uma arquitetura em camadas.
- Oferecer operações de CRUD e lógica de negócio para diversas entidades.
- Garantir autenticação e autorização com JWT.
- Aplicar validações, tratamento de exceções e uso de DTOs.
- Utilizar PostgreSQL com versionamento de banco via Flyway.

---

## 2. Escopo Mínimo de Entidades

O sistema deverá possuir **no mínimo 5 entidades relacionadas** com:

- **Relacionamentos 1:N** (um-para-muitos).  
- **Relacionamentos N:N** (muitos-para-muitos).

Exemplos (apenas referência, ajustar conforme o domínio escolhido):

- Exemplo Netflix-like: `Usuário → Perfil → Filme → Categoria → Avaliação`
- Exemplo Jogo: `Jogador → Partida → Personagem → Item → Conquista`

### 2.1. Entidades obrigatórias (genéricas)

Cada equipe deve definir pelo menos 5 entidades principais, por exemplo:

- `Entidade_1` (ex.: Usuário, Cliente, Jogador)
- `Entidade_2` (ex.: Perfil, Pedido, Partida)
- `Entidade_3`
- `Entidade_4`
- `Entidade_5`

Cada aluno do grupo deve ser responsável por **uma entidade principal** e suas operações:

- CRUD completo.
- Regras de negócio associadas.

---

## 3. Requisitos Funcionais (RF)

### RF01 – CRUD das entidades principais
O sistema deve permitir **criação, leitura, atualização e exclusão (CRUD)** de pelo menos **5 entidades principais**, incluindo:

- Endpoints para criar recursos.
- Endpoints para listar/filtrar recursos.
- Endpoints para atualizar recursos.
- Endpoints para excluir recursos (soft delete ou hard delete, conforme decisão da equipe).

### RF02 – Relacionamentos entre entidades
O sistema deve manter corretamente os **relacionamentos 1:N e N:N** entre as entidades, incluindo:

- Associação de registros (ex.: vincular um filme a uma categoria).
- Desassociação quando necessário.
- Garantia de integridade referencial no banco de dados.

### RF03 – Autenticação e autorização via JWT
O sistema deve implementar **autenticação e autorização** utilizando **JWT (JSON Web Token)**:

- Endpoint de **login** que gera um token JWT válido.
- Validação do token em endpoints protegidos.
- Diferenciação de acessos (ex.: recursos que exigem usuário autenticado).

### RF04 – Validação de dados de entrada
O sistema deve validar os dados recebidos nas requisições:

- Uso de anotações de validação (ex.: `@NotBlank`, `@Email`, `@Size`, etc.).
- Retorno de mensagens claras em caso de erro de validação.

### RF05 – Tratamento centralizado de exceções
O sistema deve possuir **tratamento centralizado de exceções**:

- Uso de `@ControllerAdvice` e `@ExceptionHandler`.
- Retorno de respostas padronizadas para erros (HTTP status code + mensagem).
- Diferenciação entre erros de validação, negócio e erros internos.

### RF06 – Uso de DTOs (Data Transfer Objects)
O sistema deve utilizar **DTOs para entrada e saída de dados**, evitando expor diretamente as entidades do JPA:

- DTOs de requisição (input).
- DTOs de resposta (output).
- Mapeamento entre Entidade ↔ DTO (manual ou com libs de mapeamento, se desejado).

### RF07 – Logs de operações importantes (bônus)
O sistema **deverá preferencialmente** registrar logs básicos (requisito opcional para bônus):

- Logs de autenticação (login, falhas).
- Logs de operações críticas (criação, atualização, exclusão de recursos).
- Uso da infra de logging do Spring Boot.

---

## 4. Requisitos Não Funcionais (RNF)

### RNF01 – Arquitetura em camadas
O sistema deve seguir **arquitetura em camadas**, contendo no mínimo:

- Camada **Controller** (exposição das APIs REST).
- Camada **Service** (regras de negócio).
- Camada **Repository** (acesso a dados – Spring Data JPA).
- Camada **Entity** (mapeamento das entidades JPA).
- Camada **DTO**.
- Mecanismo de **Validation**.
- Camada de **ExceptionHandler** centralizado.

### RNF02 – Tecnologias de backend
O backend deve ser desenvolvido com:

- **Spring Boot 3+**
- **Spring Data JPA**
- **Spring Security**
- **JWT**
- **Lombok** (para reduzir boilerplate)

### RNF03 – Banco de dados
O sistema deve utilizar:

- Banco de dados **PostgreSQL** (versão 12 a 17).
- Configuração adequada de conexão (URL, usuário, senha, etc.).
- Criação de tabelas e relacionamentos compatíveis com as entidades do sistema.

### RNF04 – Versionamento de banco (migrations)
O sistema deve utilizar **Flyway** para versionamento do banco:

- Scripts/migrations de criação das tabelas.
- Scripts de alterações de estrutura, quando necessário.
- Controle de versão para facilitar deploy e reprodutibilidade do ambiente.

### RNF05 – Versionamento de código
O projeto deve ser versionado com:

- **Git + GitHub** (repositório por equipe).
- Todos os membros devem contribuir no repositório (commits próprios).

### RNF06 – Documentação da API
A API deve estar documentada através de:

- **Swagger/OpenAPI**, **ou**
- Um arquivo **README.md** com:
  - Descrição do sistema.
  - Instruções para rodar o projeto.
  - Estrutura de pastas.
  - Exemplos de requests e responses.
  - Credenciais de acesso (usuário/senha padrão para testes).

### RNF07 – Qualidade de código
O código deve:

- Ser legível, organizado e padronizado.
- Utilizar boas práticas de nomenclatura e organização de pacotes.
- Evitar código duplicado e lógica desnecessária.
- Conter tratamento de erros consistente.

---

## 5. Requisitos de Entrega (perspectiva de software pronto)

Embora sejam requisitos de projeto (não diretamente do sistema em produção), o software deve estar em condições de permitir:

- **Documento de Análise** contendo:
  - Nome do sistema e propósito.
  - Responsabilidades de cada integrante da equipe.
  - Requisitos funcionais e não funcionais (este documento).
  - Diagramas de classes e de relacionamento (UML).

- **Modelo de Banco de Dados (DER)** e migrations do Flyway compatíveis com o código.

- **Protótipo das telas** (Figma ou similar), representando:
  - Fluxo principal do sistema (login, CRUDs, listagens).
  - Interface de referência para os principais casos de uso.

- **Demonstração das APIs** funcionando (Postman, Swagger ou frontend opcional).

---

## 6. Resumo dos Requisitos Obrigatórios

- [ ] Mínimo de 5 entidades principais com relacionamentos 1:N e N:N.  
- [ ] CRUD completo para as entidades principais.  
- [ ] Autenticação e autorização via JWT.  
- [ ] Validações com anotações (`@NotBlank`, `@Email`, `@Size`, etc.).  
- [ ] Tratamento centralizado de exceções (`@ControllerAdvice`).  
- [ ] Uso de DTOs para entrada e saída de dados.  
- [ ] Arquitetura em camadas (Controller, Service, Repository, Entity, DTO, Validation, ExceptionHandler).  
- [ ] Banco de dados PostgreSQL com migrations via Flyway.  
- [ ] Projeto usando Spring Boot 3, Spring Data JPA, Spring Security, JWT e Lombok.  
- [ ] Versionamento com Git + GitHub.  
- [ ] Documentação via Swagger/OpenAPI ou README.md.  
- [ ] Logs básicos de operações importantes (para bônus).
