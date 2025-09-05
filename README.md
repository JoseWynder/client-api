# NeoApp - Clients API

## Descrição
MVP desenvolvido como parte do desafio técnico da NeoApp para a vaga de Desenvolvedor Back-End Estagiário.  
A aplicação expõe uma API REST para cadastro e gerenciamento de clientes (CRUD completo), com autenticação baseada em JWT.

---

## Tecnologias utilizadas
- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Security + JWT
- H2 Database (em memória)
- Swagger / OpenAPI

---

## Como executar
1. Clone o repositório e abra em sua IDE ou execute via Maven:
   ```bash
   mvn spring-boot:run
   ```
2. A aplicação será iniciada em:
   ```
   http://localhost:8080
   ```

---

## Autenticação
A API utiliza **JWT** para autenticação.

- Endpoint de login:
  ```
  POST /api/users/auth
  ```
- Credenciais padrão:
  ```json
  {
    "username": "NeoApp",
    "password": "username"
  }
  ```
- Resposta: token JWT que deve ser usado no header:
  ```
  Authorization: Bearer <token>
  ```

---

## Endpoints principais

### Clients
- `POST /api/clients` → cria novo cliente  
- `GET /api/clients/{id}` → busca cliente por ID  
- `PUT /api/clients/{id}` → atualiza cliente existente  
- `DELETE /api/clients/{id}` → remove cliente  
- `GET /api/clients` → lista clientes (paginação e filtros disponíveis por atributos)

### Users / Auth
- `POST /api/users/auth` → autenticação e geração de token JWT  

---

## Banco de dados
- Utiliza **H2 em memória**  
- Scripts `schema.sql` e `data.sql` já criam a estrutura e inserem registros iniciais  
- Não é necessário configuração adicional

---

## Documentação da API
Swagger UI disponível em:
```
http://localhost:8080/swagger-ui/index.html
```
