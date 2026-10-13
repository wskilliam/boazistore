# 🥟 Baozi Store – API REST

API REST para controle básico de **clientes, produtos e pedidos** da *Baozi Store*, uma pequena loja de pão chinês (baozi). Desenvolvida como atividade prática da disciplina **Desenvolvimento Web Back-End**.

## 📋 Sobre o projeto

A Baozi Store precisa informatizar o controle de clientes, produtos e pedidos. Esta API permite:

- Cadastrar, consultar, listar, atualizar e apagar **clientes**
- Cadastrar, consultar, listar, atualizar e apagar **produtos**
- Registrar **pedidos simples**: um cliente compra **um único produto** em uma determinada quantidade

## 🛠️ Tecnologias

- Java 21
- Spring Boot 3.3
- Spring Web (MVC)
- Spring Data JPA / Hibernate
- MySQL (hospedado no Railway)
- Maven
- Postman (testes)

## 🗂️ Estrutura do projeto

```
src/main/java/com/baozi/baozistore
├── BaozistoreApplication.java
├── model/         # Entidades JPA (Cliente, Produto, Pedido) e PedidoRequest
├── repository/    # Interfaces JpaRepository
└── controller/    # Controllers REST
src/main/resources
└── application.properties
```

## 🧩 Modelo de dados

| Entidade | Campos |
|---|---|
| **Cliente** | `id` (Long), `nome` (String), `clienteDesde` (LocalDate) |
| **Produto** | `id` (Long), `nome` (String), `preco` (BigDecimal), `estoque` (Boolean) |
| **Pedido** | `id` (Long), `cliente` (FK → Cliente), `produto` (FK → Produto), `quantidade` (Integer) |

Relacionamentos: um cliente faz **muitos** pedidos; um produto é vendido em **muitos** pedidos.

## 🚀 Como executar

**Pré-requisitos:** JDK 17+ e Maven (ou use a IDE).

1. Clone o repositório:
   ```bash
   git clone https://github.com/SEU-USUARIO/baozistore.git
   cd baozistore
   ```
2. Configure o banco em `src/main/resources/application.properties`. A senha é lida da variável de ambiente `DB_PASSWORD`:
   ```bash
   # Linux / macOS
   export DB_PASSWORD=****
   # Windows (PowerShell)
   $env:DB_PASSWORD=****
   ```
   > ⚠️ Nunca faça commit de senhas reais.
   > Para usar outro MySQL local, altere `spring.datasource.url` e `spring.datasource.username`.
3. Execute:
   ```bash
   mvn spring-boot:run
   ```
   (ou rode a classe `BaozistoreApplication` na IDE)
4. A API ficará disponível em **http://localhost:8080**.

As tabelas são criadas automaticamente (`spring.jpa.hibernate.ddl-auto=update`).

## 🔌 Endpoints

Os mesmos endpoints existem para `/clientes`, `/produtos` e `/pedidos`:

| Método | Rota | Descrição | Sucesso |
|---|---|---|---|
| POST | `/{recurso}` | Criar | 201 |
| GET | `/{recurso}` | Listar todos | 200 |
| GET | `/{recurso}/{id}` | Consultar por ID | 200 (404 se não existir) |
| PUT | `/{recurso}/{id}` | Atualizar *(opcional)* | 200 |
| DELETE | `/{recurso}/{id}` | Apagar | 204 |

### Exemplos de requisição (JSON)

**POST /clientes**
```json
{ "nome": "SeuNome123456" }
```

**POST /produtos**
```json
{ "nome": "Baozi de Carne", "preco": 8.50, "estoque": true }
```

**POST /pedidos**
```json
{ "clienteId": 1, "produtoId": 1, "quantidade": 10 }
```

Resposta de exemplo do pedido:
```json
{
  "id": 1,
  "cliente": { "id": 1, "nome": "Nome", "clienteDesde": "2026-10-12" },
  "produto": { "id": 1, "nome": "Baozi de Carne", "preco": 8.50, "estoque": true },
  "quantidade": 10
}
```

### Regras e observações
- `POST /pedidos` retorna **404** se o cliente ou o produto não existir, e **400** se faltar algum campo ou a quantidade for ≤ 0.
- Se `clienteDesde` não for enviado, a data atual é usada.
- Apague os pedidos antes de apagar o cliente ou o produto relacionado (chave estrangeira).

## 🧪 Testes

Os endpoints foram testados com o Postman (capturas de tela no PDF da atividade).

## 👤 Autor

**William Rodrigues** – Desenvolvimento Web Back-End – UNINTER