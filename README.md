# Documentação do Projeto

[Obs do projeto]: Não foram implementados todos os testes unitários, apenas alguns para agilizar o tempo de execução do projeto. Caso necessário a documentação dos end-points detalhados está no swagger. 
Foi implementado o nivel 3.

## Requisitos
Antes de começar, certifique-se de ter os seguintes softwares instalados:
- [Java 17+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven 3.8.8](https://maven.apache.org/install.html)
- [PostgreSQL](https://www.postgresql.org/download/)

## Configuração do Ambiente

### 1. Clonando o Repositório
Primeiro, você precisará clonar este repositório. Execute o seguinte comando no terminal:
```bash
git clone https://github.com/Dalenson/SeniorProjectAvaliation.git
```

### 2. Criando Banco Postgrees
Com postgree instalado e configurado executar:
```bash
CREATE DATABASE banco;
GRANT ALL PRIVILEGES ON DATABASE banco TO seu_usuario;
```

### 2.1 Ajustando variaveis de ambiente do banco
Acessar arquivo ```application.properties``` e ajustar as variaveis username e password conforme configuradas no banco de dados do postgrees.

### 3. Compilando e Executando o Projeto
Dentro da pasta do projeto, compile e execute o projeto com Maven:
```bash
mvn clean install
mvn spring-boot:run
```

### 4. Executando os Testes
Para garantir que o projeto está funcionando corretamente, você pode executar os testes com o seguinte comando:
```bash
mvn test
```

### 5. Documentação com Swagger
A documentação da API é fornecida pelo Swagger, que permite explorar e testar os endpoints da API diretamente no navegador. 

Para acessar a documentação do Swagger, execute o projeto e abra o seguinte URL em seu navegador:
```bash
http://localhost:8080/documentacao.html
```
Nesta interface, você pode visualizar todos os endpoints disponíveis, suas descrições, e testar as requisições diretamente.

## Funcionalidades do Projeto

### 1. Cadastro de Produtos/Serviços
Este sistema permite o cadastro de produtos e serviços com as seguintes funcionalidades:
- Criar, listar, atualizar e excluir produtos/serviços.
- Aplicação de desconto para produtos.

### 2. Gestão de Pedidos
- Criação de pedidos, incluindo produtos e serviços.
- Aplicação de descontos para produtos dentro de um pedido.
- Validação de status e regras de negócio antes da confirmação do pedido.

### 3. API REST
O sistema expõe uma API RESTful com os seguintes endpoints principais:

- **Produtos/Serviços**:
  - `GET /produto-servico`: Lista todos os produtos/serviços.
  - `POST /produto-servico`: Cria um novo produto/serviço.
  - `PUT /produto-servico/{id}`: Atualiza um produto/serviço existente.
  - `DELETE /produto-servico/{id}`: Exclui um produto/serviço.

- **Pedidos**:
  - `GET /pedidos`: Lista todos os pedidos.
  - `GET /pedidos/{id}`: Lista um pedido existente.
  - `POST /pedidos`: Cria um novo pedido.
  - `POST /pedidos/aplicar-desconto`: Aplica um desconto ao pedido.
  - `PUT /pedidos/{id}`: Atualiza um pedido existente.
  - `DELETE /pedidos/{id}`: Exclui um pedido.
    
- **Itens do Pedido**:
  - `GET /pedido-item`: Lista todos os itens do pedido.
  - `POST /pedido-item`: Cria um novo item no pedido.
  - `PUT /pedido-item/{id}`: Atualiza um item no pedido.
  - `DELETE /pedido-item/{id}`: Exclui um item no pedido.

## Executando em Ambiente de Produção
Para rodar o projeto em ambiente de produção, utilize o seguinte comando para gerar o JAR:

```bash
mvn clean package
java -jar target/seu-projeto.jar
```
