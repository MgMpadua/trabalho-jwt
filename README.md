Guia Prático para Utilizar a API de Gerenciamento de Usuários

Este guia fornece um passo a passo claro e conciso para interagir com a API RESTful de gerenciamento de usuários, que utiliza autenticação baseada em JWT (JSON Web Token). A API permite operações de registro, login, gerenciamento de perfil e administração de usuários, com permissões diferenciadas para usuários comuns (USER) e administradores (ADMIN).

----------------------------------------------------------------

Pré-requisitos

- Ferramenta de Teste: Postman ou outra ferramenta para requisições HTTP.
- Aplicação em Execução: Certifique-se de que a aplicação está rodando localmente em http://localhost:8080.
- Console H2 (Opcional): Para verificar os dados no banco, acesse http://localhost:8080/h2-console com a URL jdbc:h2:mem:testdb, usuário, e senha vazia.

---

Visão Geral dos Endpoints

Autenticação:

- POST /api/auth/register: Cria um novo usuário.
- POST /api/auth/login: Autentica um usuário e retorna um token JWT.

Gerenciamento de Perfil (Autenticado):

- GET /api/user/profile: Recupera os dados do usuário logado.
- PUT /api/user/profile: Atualiza os dados do usuário logado.

Administração (Apenas ADMIN):

- GET /api/admin/users: Lista todos os usuários.
- PUT /api/admin/users/{id}: Atualiza um usuário específico pelo ID.
- DELETE /api/admin/users/{id}: Remove um usuário pelo ID.

---

Passo a Passo para Interagir com a API

1. Criar um Novo Usuário

   - Objetivo: Registrar um usuário no sistema.
   - Método: POST
   - URL: http://localhost:8080/api/auth/register
   - Corpo da Requisição (JSON): { "nome": "João Silva", "email": "joao.silva@email.com", "senha": "senhaSegura123", "role": "USER" }
   - Cabeçalhos: Nenhum (endpoint público).
   - Resultado Esperado:
     - Status: 200 OK
     - Resposta: "Usuario registrado com sucesso"
   - Comando cURL: curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d '{"nome":"João Silva","email":"joao.silva@email.com","senha":"senhaSegura123","role":"USER"}'
   - Dica: Para criar um administrador, use "role": "ADMIN".

2. Autenticar e Obter um Token JWT

   - Objetivo: Fazer login e receber um token para autenticação.
   - Método: POST
   - URL: http://localhost:8080/api/auth/login
   - Corpo da Requisição (JSON): { "email": "joao.silva@email.com", "senha": "senhaSegura123" }
   - Cabeçalhos: Nenhum (endpoint público).
   - Resultado Esperado:
     - Status: 200 OK
     - Resposta: Um token JWT, como: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
   - Comando cURL: curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"email":"joao.silva@email.com","senha":"senhaSegura123"}'
   - Dica: Salve o token retornado, pois ele será necessário para as próximas requisições autenticadas.

3. Consultar o Perfil do Usuário Logado

   - Objetivo: Visualizar os dados do usuário autenticado.
   - Método: GET
   - URL: http://localhost:8080/api/user/profile
   - Cabeçalhos:
     - Authorization: Bearer
   - Corpo da Requisição: Nenhum.
   - Resultado Esperado:
     - Status: 200 OK
     - Resposta: { "id": 1, "nome": "João Silva", "email": "joao.silva@email.com", "role": "USER" }
   - Comando cURL: curl -X GET http://localhost:8080/api/user/profile -H "Authorization: Bearer "
   - Dica: Certifique-se de que o token é válido e não expirou.

4. Atualizar o Perfil do Usuário Logado

   - Objetivo: Modificar os dados do usuário autenticado (nome ou senha).
   - Método: PUT
   - URL: http://localhost:8080/api/user/profile
   - Cabeçalhos:
     - Authorization: Bearer
   - Corpo da Requisição (JSON): { "nome": "João Silva Atualizado", "senha": "novaSenha456" }
   - Resultado Esperado:
     - Status: 200 OK
     - Resposta: "Alterado com sucesso"
   - Comando cURL: curl -X PUT http://localhost:8080/api/user/profile -H "Content-Type: application/json" -H "Authorization: Bearer " -d '{"nome":"João Silva Atualizado","senha":"novaSenha456"}'
   - Dica: O campo "senha" é opcional. Se omitido, a senha atual será mantida.

5. Listar Todos os Usuários (Apenas ADMIN)

   - Objetivo: Recuperar a lista de todos os usuários cadastrados.
   - Método: GET
   - URL: http://localhost:8080/api/admin/users
   - Cabeçalhos:
     - Authorization: Bearer
   - Corpo da Requisição: Nenhum.
   - Resultado Esperado:
     - Status: 200 OK
     - Resposta: \[ { "id": 1, "nome": "João Silva", "email": "joao.silva@email.com", "role": "USER" }, { "id": 2, "nome": "Admin", "email": "admin@email.com", "role": "ADMIN" } \]
   - Comando cURL: curl -X GET http://localhost:8080/api/admin/users -H "Authorization: Bearer "
   - Dica: Use um token de um usuário com "role": "ADMIN". Crie um usuário admin via /api/auth/register se necessário.

6. Atualizar um Usuário pelo ID (Apenas ADMIN)

   - Objetivo: Modificar os dados de um usuário específico.
   - Método: PUT
   - URL: http://localhost:8080/api/admin/users/1
   - Cabeçalhos:
     - Authorization: Bearer
   - Corpo da Requisição (JSON): { "nome": "João Silva Modificado", "email": "joao.modificado@email.com", "role": "USER", "senha": "novaSenha789" }
   - Resultado Esperado:
     - Status: 200 OK
     - Resposta: "Alterado com sucesso"
   - Comando cURL: curl -X PUT http://localhost:8080/api/admin/users/1 -H "Content-Type: application/json" -H "Authorization: Bearer " -d '{"nome":"João Silva Modificado","email":"joao.modificado@email.com","role":"USER","senha":"novaSenha789"}'
   - Dica: O campo "senha" é opcional.

7. Deletar um Usuário pelo ID (Apenas ADMIN)

   - Objetivo: Remover um usuário do sistema.
   - Método: DELETE
   - URL: http://localhost:8080/api/admin/users/1
   - Cabeçalhos:
     - Authorization: Bearer
   - Corpo da Requisição: Nenhum.
   - Resultado Esperado:
     - Status: 200 OK
     - Resposta: "Usuário deletado com sucesso"
   - Comando cURL: curl -X DELETE http://localhost:8080/api/admin/users/1 -H "Authorization: Bearer "

---

Dicas para um Uso Eficiente

1. Gerenciamento do Token JWT:

   - Sempre inclua o token no cabeçalho "Authorization: Bearer " para endpoints autenticados.
   - O token tem uma validade de 24 horas (definida no application.properties). Se expirar, faça login novamente.

2. Permissões:

   - Endpoints em /api/admin/\* exigem um token de um usuário com "role": "ADMIN". Crie um usuário admin via /api/auth/register com "role": "ADMIN".

3. Solução de Problemas Comuns:

   - 401 Unauthorized: Token ausente, inválido ou expirado. Verifique o token e o cabeçalho Authorization.
   - 403 Forbidden: O usuário não tem permissão (ex.: tentar acessar /api/admin/users com um token de USER).
   - 400 Bad Request: Dados inválidos no corpo da requisição. Verifique o formato JSON e os campos obrigatórios (nome, email).
   - 404 Not Found: O recurso não existe (ex.: tentar atualizar/deletar um ID de usuário inexistente).

4. Validações:

   - Se você adicionou anotações como @NotBlank e @Email ao UserModel.java, certifique-se de que os dados enviados atendem a essas restrições.
   - Exemplo de erro: Enviar um email inválido (ex.: "email": "invalido") resultará em uma resposta 400 com detalhes do erro.

5. Verificação do Banco:

   - Use o console H2 (http://localhost:8080/h2-console) para inspecionar a tabela USER_MODEL e confirmar que os dados estão sendo salvos corretamente.

---

Exemplo de Fluxo Completo

1. Registre um usuário comum: POST /api/auth/register com {"nome":"João Silva","email":"joao.silva@email.com","senha":"senhaSegura123","role":"USER"}
2. Faça login: POST /api/auth/login com {"email":"joao.silva@email.com","senha":"senhaSegura123"} Salve o token retornado.
3. Consulte o perfil: GET /api/user/profile com Authorization: Bearer
4. Registre um admin: POST /api/auth/register com {"nome":"Admin","email":"admin@email.com","senha":"admin123","role":"ADMIN"}
5. Faça login como admin: POST /api/auth/login com {"email":"admin@email.com","senha":"admin123"}
6. Liste todos os usuários: GET /api/admin/users com Authorization: Bearer
7. Atualize ou delete um usuário: Use PUT /api/admin/users/1 ou DELETE /api/admin/users/1 com o token do admin.

---

Notas Finais

- Segurança: Certifique-se de que a propriedade jwt.secret no application.properties é uma chave segura e não está exposta em repositórios públicos.
- Produção: Para um ambiente de produção, substitua o H2 por um banco de dados como MySQL ou PostgreSQL e configure HTTPS.
- Logs: Se encontrar erros, habilite logs detalhados no application.properties: logging.level.org.springframework=DEBUG logging.level.com.example.trabalho=DEBUG
- Testes: Considere adicionar testes automatizados para validar os endpoints.
