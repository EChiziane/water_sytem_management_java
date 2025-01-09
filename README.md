# Sistema de Gestão de Água (Water System Management)

Este projeto é um sistema de gestão de clientes e pagamentos para um sistema de distribuição de água. O sistema permite o controle de clientes, faturas e pagamentos, além de gerenciar informações sobre o consumo e o estado das válvulas de cada cliente.

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação principal.
- **Spring Boot**: Framework para o desenvolvimento do backend.
- **JPA (Java Persistence API)**: Para a interação com o banco de dados.
- **H2 Database**: Banco de dados embutido para desenvolvimento (pode ser substituído por outros bancos, como MySQL ou PostgreSQL).
- **REST API**: Para comunicação entre o cliente e o servidor.
- **JSON**: Formato utilizado para troca de dados na API.

## Funcionalidades

### 1. **Cadastro de Clientes**
- Os clientes podem ser cadastrados no sistema com informações como nome, contato, endereço, e estado (ativo ou inativo).
- O sistema permite definir a válvula associada a cada cliente e controlar o número de meses em dívida.

### 2. **Gestão de Faturas**
- Cada cliente pode ter várias faturas associadas, com informações sobre a data de emissão e os detalhes da fatura.
  
### 3. **Gestão de Pagamentos**
- Os pagamentos podem ser registrados, incluindo o valor pago, a data de pagamento, o método de pagamento e se o pagamento foi confirmado.

### 4. **API REST**
- O sistema fornece uma API REST para interação com os dados de clientes, faturas e pagamentos.
  
## Endpoints da API

### **Clientes**

- **POST /customers**: Cria um novo cliente.
    - Exemplo de request body:
      ```json
      {
        "name": "João Silva",
        "contact": "+258841234567",
        "address": "Av. Eduardo Mondlane, Maputo",
        "active": true,
        "valve": "Válvula 1",
        "mesesEmDivida": 0
      }
      ```
  
- **GET /customers**: Retorna a lista de todos os clientes.
  
- **GET /customers/{id}**: Retorna um cliente específico, dado o `id`.

- **DELETE /customers/{id}**: Deleta um cliente com o ID especificado.

### **Faturas**

- **POST /invoices**: Cria uma nova fatura.
    - Exemplo de request body:
      ```json
      {
        "issueDate": "2025-01-01T00:00:00",
        "details": "Fatura referente ao consumo de água de janeiro"
      }
      ```

- **GET /invoices**: Retorna a lista de todas as faturas.

- **GET /invoices/{id}**: Retorna uma fatura específica, dado o `id`.

- **DELETE /invoices/{id}**: Deleta uma fatura com o ID especificado.

### **Pagamentos**

- **POST /payments**: Registra um novo pagamento.
    - Exemplo de request body:
      ```json
      {
        "amount": 100.50,
        "paymentDate": "2025-01-05T00:00:00",
        "referenceMonth": "Janeiro",
        "paymentMethod": "Transferência Bancária",
        "confirmed": true
      }
      ```

- **GET /payments**: Retorna a lista de todos os pagamentos.

- **GET /payments/{id}**: Retorna um pagamento específico, dado o `id`.

- **DELETE /payments/{id}**: Deleta um pagamento com o ID especificado.

## Instalação

### 1. **Clone o Repositório**
Clone este repositório em sua máquina local utilizando o Git:

```bash
git clone https://github.com/seu-usuario/water-system-management.git
