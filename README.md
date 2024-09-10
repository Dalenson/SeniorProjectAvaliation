# Documentação do Projeto

## Requisitos
Antes de começar, certifique-se de ter os seguintes softwares instalados:
- [Java 8+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/install.html)
- [PostgreSQL](https://www.postgresql.org/download/)

## Configuração do Ambiente

### 1. Clonando o Repositório
Primeiro, você precisará clonar este repositório. Execute o seguinte comando no terminal:
```bash
git clone https://github.com/Dalenson/SeniorProjectAvaliation.git
```

### 2. Compilando e Executando o Projeto
Dentro da pasta do projeto, compile e execute o projeto com Maven:
```bash
mvn clean install
mvn spring-boot:run
```

### 3. Executando os Testes
Para garantir que o projeto está funcionando corretamente, você pode executar os testes com o seguinte comando:
```bash
mvn test
```

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
  - `GET /produtos`: Lista todos os produtos/serviços.
  - `POST /produtos`: Cria um novo produto/serviço.
  - `PUT /produtos/{id}`: Atualiza um produto/serviço existente.
  - `DELETE /produtos/{id}`: Exclui um produto/serviço.

- **Pedidos**:
  - `GET /pedidos`: Lista todos os pedidos.
  - `POST /pedidos`: Cria um novo pedido.
  - `PUT /pedidos/{id}`: Atualiza um pedido existente.
  - `DELETE /pedidos/{id}`: Exclui um pedido.

## Executando em Ambiente de Produção
Para rodar o projeto em ambiente de produção, utilize o seguinte comando para gerar o JAR:

```bash
mvn clean package
java -jar target/seu-projeto.jar
```
