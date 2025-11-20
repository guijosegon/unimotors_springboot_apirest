# UniMotors - API

API REST para marketplace de veículos (carros/motos) construída com Spring Boot 3, Spring Security (JWT), Spring Data JPA, Flyway e PostgreSQL, seguindo os requisitos do trabalho de backend.

## Tecnologias
- Java 17+ / Maven 3.8+  
- Spring Boot 3.2.x (web, data-jpa, security, validation)  
- JWT (`jjwt`), Springdoc OpenAPI (Swagger)  
- PostgreSQL 12+ com Flyway (`V1__init.sql`)  

## Estrutura principal
- `pom.xml` – dependências e build Maven  
- `src/main/java/br/com/unimotors/UniMotorsApplication.java` – classe principal  
- `src/main/resources/application.properties` – configuração (datasource, Flyway, JWT, porta)  
- `src/main/resources/db/migration/V1__init.sql` – migration inicial com todo o schema (usuarios, marcas, modelos, anuncios, propostas, favoritos, lojas, etc.)  

### Módulos de domínio
- `autenticacao` – `AuthController`, `TokenJwtService`, `FiltroJwt`  
- `usuario` – entidade `Usuario`, `PerfilAcesso`, DTOs, serviço, repositório, `UsuarioController` (CRUD ADMIN)  
- `catalogo` – `Marca`, `Modelo`, `EspecificacaoVeiculo`, `Opcional` + DTOs, service e `CatalogoController`  
- `anuncio` – `AnuncioVeiculo`, `ImagemAnuncio`, `StatusAnuncio` + DTOs, service e `AnuncioController`  
- `negocio` – `Proposta`, `Favorito`, `Loja`, `LojaUsuario` + DTOs, services e controllers  
- `comum/excecao` – `TratamentoExcecoesGlobal` com tratamento centralizado de erros  
- `config` – segurança (`SecurityConfig`) e Swagger (`OpenApiConfig`)  

## Configuração (variáveis de ambiente)
- `DB_HOST` (padrão: `localhost`)  
- `DB_PORT` (padrão: `5433`, ver `application.properties`)  
- `DB_NAME` (padrão: `unimotors`)  
- `DB_USER` (padrão: `postgres`)  
- `DB_PASSWORD` (padrão: `postgres`)  
- `APP_JWT_SEGREDO` (segredo do JWT – altere em produção)  

## Rodando localmente
1. Criar o banco no PostgreSQL:

   ```sql
   CREATE DATABASE unimotors;
   ```

2. Rodar a aplicação:

   ```bash
   mvn spring-boot:run
   ```

   O Flyway aplicará `V1__init.sql` automaticamente na primeira execução.

3. Acessar Swagger/OpenAPI:

- `http://localhost:8088/swagger-ui/index.html`  

## Fluxo básico de uso
1. **Registrar usuário** (público):  
   `POST /api/autenticacao/registrar`  
   Corpo (exemplo):  
   ```json
   {
     "nome": "Admin",
     "email": "admin@unimotors.com",
     "senha": "senha1234",
     "perfil": "ADMIN"
   }
   ```
2. **Login**:  
   `POST /api/autenticacao/login` → retorna token JWT em `tokenAcesso`.  
   Enviar o token no header: `Authorization: Bearer <token>`.

3. **Principais endpoints** (resumo)
- Usuários (ADMIN): `GET/POST/PUT/DELETE /api/usuarios`  
- Catálogo:  
  - `GET /api/marcas` · `POST /api/marcas` (ADMIN)  
  - `GET /api/marcas/{id}/modelos`, `POST /api/modelos` (ADMIN)  
  - `GET /api/modelos/{id}/especificacoes`, `POST /api/especificacoes` (ADMIN)  
  - `GET /api/opcionais` · `POST /api/opcionais` (ADMIN)  
- Anúncios:  
  - `GET /api/anuncios?marca=&modelo=&precoMin=&cidade=&page=`  
  - `GET /api/anuncios/{id}`  
  - `POST /api/anuncios` (VENDEDOR/ADMIN)  
  - `PUT /api/anuncios/{id}`, `PATCH /api/anuncios/{id}/status`, `DELETE /api/anuncios/{id}` (proprietário/ADMIN)  
- Propostas:  
  - `POST /api/anuncios/{id}/propostas` (COMPRADOR)  
  - `GET /api/anuncios/{id}/propostas` (proprietário/ADMIN)  
  - `PATCH /api/propostas/{id}/aceitar` / `recusar` (proprietário/ADMIN)  
- Favoritos:  
  - `POST /api/anuncios/{id}/favoritos`  
  - `DELETE /api/anuncios/{id}/favoritos`  
  - `GET /api/meus/favoritos`  
- Lojas (ADMIN):  
  - `GET /api/lojas` · `POST /api/lojas`  
  - `POST /api/lojas/{id}/membros` · `DELETE /api/lojas/{id}/membros/{usuarioId}`  

## Exemplos de requests/responses

### Autenticação

**Registrar usuário**  
`POST /api/autenticacao/registrar`

Request:
```json
{
  "nome": "Admin",
  "email": "admin@unimotors.com",
  "senha": "senha1234",
  "perfil": "ADMIN"
}
```

Response (200):
```json
{
  "id": "8f4e6c70-4b70-4a5c-b7d1-1a2b3c4d5e6f",
  "nome": "Admin",
  "email": "admin@unimotors.com",
  "perfil": "ADMIN",
  "telefone": null,
  "criadoEm": "2024-11-20T14:30:15.123456"
}
```

**Login**  
`POST /api/autenticacao/login`

Request:
```json
{
  "email": "admin@unimotors.com",
  "senha": "senha1234"
}
```

Response (200):
```json
{
  "tokenAcesso": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

Uso do token em chamadas protegidas (exemplo header HTTP):
```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

Exemplo de erro de autenticação/credenciais inválidas (400/401 – formato do handler global):
```json
{
  "timestamp": "2024-11-20T14:35:01.123456Z",
  "status": 400,
  "erro": "Bad Request",
  "mensagem": "E-mail ou senha inválidos",
  "caminho": "/api/autenticacao/login"
}
```

### Catálogo (exemplo)

**Criar marca (ADMIN)**  
`POST /api/marcas`

Request:
```json
{
  "nome": "Toyota"
}
```

Response (200):
```json
{
  "id": "f3a2b1c0-1234-5678-9abc-def012345678",
  "nome": "Toyota"
}
```

### Anúncios (exemplo)

**Criar anúncio (VENDEDOR/ADMIN)**  
`POST /api/anuncios`

Request:
```json
{
  "modeloId": "11111111-2222-3333-4444-555555555555",
  "especificacaoId": "66666666-7777-8888-9999-000000000000",
  "titulo": "Corolla 2.0 XEi 2022",
  "descricao": "Carro em ótimo estado, único dono.",
  "preco": 115000.00,
  "cidade": "Criciúma",
  "estado": "SC",
  "opcionaisIds": [
    "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee"
  ],
  "urlsImagens": [
    "https://meu-bucket/imagens/corolla-1.jpg",
    "https://meu-bucket/imagens/corolla-2.jpg"
  ]
}
```

Response (200):
```json
{
  "id": "12345678-90ab-cdef-1234-567890abcdef",
  "titulo": "Corolla 2.0 XEi 2022",
  "descricao": "Carro em ótimo estado, único dono.",
  "preco": 115000.0,
  "status": "RASCUNHO",
  "cidade": "Criciúma",
  "estado": "SC",
  "marca": "Toyota",
  "modelo": "Corolla",
  "ano": 2022,
  "imagens": [
    "https://meu-bucket/imagens/corolla-1.jpg",
    "https://meu-bucket/imagens/corolla-2.jpg"
  ],
  "opcionais": [
    "Ar-condicionado",
    "Direção hidráulica"
  ],
  "proprietarioId": "8f4e6c70-4b70-4a5c-b7d1-1a2b3c4d5e6f"
}
```

### Propostas (exemplo)

**Criar proposta (COMPRADOR)**  
`POST /api/anuncios/{id}/propostas`

Request:
```json
{
  "valor": 110000.00,
  "mensagem": "Posso pagar à vista nesta semana."
}
```

Response (200):
```json
{
  "id": "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee",
  "anuncioId": "12345678-90ab-cdef-1234-567890abcdef",
  "compradorId": "99999999-8888-7777-6666-555555555555",
  "valor": 110000.0,
  "status": "PENDENTE",
  "criadoEm": "2024-11-20T15:10:30.123456"
}
```

**Aceitar proposta (proprietário/ADMIN)**  
`PATCH /api/propostas/{id}/aceitar`

Response (200):
```json
{
  "id": "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee",
  "anuncioId": "12345678-90ab-cdef-1234-567890abcdef",
  "compradorId": "99999999-8888-7777-6666-555555555555",
  "valor": 110000.0,
  "status": "ACEITA",
  "criadoEm": "2024-11-20T15:10:30.123456"
}
```

### Favoritos (exemplo)

**Favoritar anúncio (usuário autenticado)**  
`POST /api/anuncios/{id}/favoritos`

Response (204 – sem corpo).

**Listar meus favoritos**  
`GET /api/meus/favoritos`

Response (200):
```json
[
  {
    "id": "12345678-90ab-cdef-1234-567890abcdef",
    "titulo": "Corolla 2.0 XEi 2022",
    "descricao": "Carro em ótimo estado, único dono.",
    "preco": 115000.0,
    "status": "ATIVO",
    "cidade": "Criciúma",
    "estado": "SC",
    "marca": "Toyota",
    "modelo": "Corolla",
    "ano": 2022,
    "imagens": [
      "https://meu-bucket/imagens/corolla-1.jpg"
    ],
    "opcionais": [
      "Ar-condicionado",
      "Direção hidráulica"
    ],
    "proprietarioId": "8f4e6c70-4b70-4a5c-b7d1-1a2b3c4d5e6f"
  }
]
```



