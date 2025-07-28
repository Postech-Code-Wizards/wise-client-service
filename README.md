# 🧠 wise-client-service

Microserviço responsável pelo gerenciamento de dados de clientes no ecossistema Wise.

---

## 📚 Índice

- [📌 Visão Geral](#-visão-geral)
- [🧱 Arquitetura](#-arquitetura)
- [🔌 Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [🚀 Endpoints](#-endpoints)
- [🧪 Testes e Cobertura](#-testes-e-cobertura)
- [🐳 Como Executar](#-como-executar)

---

## 📌 Visão Geral

O `wise-client-service` é responsável pelo **gerenciamento dos dados de clientes** no ecossistema de microsserviços do projeto **Wise**.

Ele permite o cadastro, atualização, busca e exclusão de informações como **nome, CPF, data de nascimento e endereços**.

> ⚠️ O sistema valida duplicidade de CPF e impede cadastros inválidos.

---

## 🧱 Arquitetura

O serviço segue os princípios da **Arquitetura Limpa**, com separação clara entre camadas:

- `domain.model` - Entidades e regras de negócio
- `usecase` - Casos de uso
- `gateway` - Comunicação com banco de dados (JPA + Panache)
- `controller` - Endpoints REST
- `mapper` - Conversão entre entidades e DTOs

Há **isolamento completo entre as camadas**, garantindo baixo acoplamento:
- Os **use cases** não conhecem os DTOs de request/response nem a camada web.
- Os **controllers** não acessam diretamente as entidades do domínio, utilizando DTOs específicos.

---

## 🔌 Tecnologias Utilizadas

- Java 21 + **Quarkus**
- **PostgreSQL**
- **Hibernate Panache**
- **Flyway** para versionamento de banco
- **JaCoCo** para cobertura de testes
- **JUnit 5** + **Mockito**
- **Docker** + Docker Compose

---

## 🚀 Endpoints

Todos os endpoints são expostos via `/clientes`:

| Método | Endpoint                 | Descrição                                   |
|--------|--------------------------|---------------------------------------------|
| GET    | `/clientes`              | Lista todos os clientes                     |
| GET    | `/clientes/{id}`         | Busca cliente por ID                        |
| GET    | `/clientes/cpf/{cpf}`    | Busca cliente por CPF                       |
| POST   | `/clientes`              | Cria um novo cliente                        |
| PUT    | `/clientes/{id}`         | Atualiza um cliente                         |
| DELETE | `/clientes/{id}`         | Remove cliente por ID                       |

---

## 🧪 Testes e Cobertura

O projeto utiliza **JUnit 5** + **Mockito** para testes unitários e cobertura via **JaCoCo**.

> ⚠️ Como o serviço utiliza Quarkus, é necessário o seguinte comando para gerar o relatório:

```bash
./mvnw clean test jacoco:report
```

📂 O relatório estará disponível em:

```
target/jacoco-report/index.html
```

---

## 🐳 Como Executar

### ✅ Localmente com Quarkus

```bash
./mvnw clean quarkus:dev
```

A aplicação será iniciada em:
```
http://localhost:8080
```

### ✅ Com Docker

Execute:

```bash
docker compose up --build
```

---

> Este microserviço integra o projeto final da Fase 4 do Pós-Tech em Arquitetura de Software (FIAP), como parte de um sistema de gerenciamento de pedidos baseado em microsserviços.
