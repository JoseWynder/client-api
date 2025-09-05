# NeoApp - Clients API

## Descrição
MVP desenvolvido como parte do desafio técnico da NeoApp para a vaga de Desenvolvedor Back-End.  
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
1. Clone o repositório e abra em uma IDE ou execute via Maven na raiz do projeto:
   ```bash
   mvn spring-boot:run
   ```
2. A aplicação será iniciada em:
   ```
   http://localhost:8080
   ```
3. Caso queira acessar a API diretamente na nuvem, utilize a URL:
   ```
   https://client-api-3r7c.onrender.com
   ```

   > **Observação:** A URL da nuvem é temporária e será substituída quando a aplicação sair de produção.  
   > As requisições podem demorar um pouco mais que o normal, especialmente na primeira chamada, devido à inicialização do servidor. Isso é comportamento do ambiente e não da aplicação.


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
- `GET /api/clients` → lista todos os clientes (com paginação)
- `GET /api/clients/search` → busca avançada com filtros (com paginação)

### Users / Auth
- `POST /api/users/auth` → autenticação e geração de token JWT  

---

## Banco de dados
- Utiliza H2 em memória 
- Scripts `schema.sql` e `data.sql` já criam a estrutura e inserem registros iniciais  
- Não é necessário configuração adicional

---

## Documentação da API
Swagger UI disponível em:
- Local: http://localhost:8080/swagger-ui/index.html
- Nuvem: https://client-api-3r7c.onrender.com/swagger-ui/index.html
  
