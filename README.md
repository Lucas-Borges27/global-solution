# Global Solution

## Visão Geral do Projeto

O Alerta360 é um serviço backend REST API construído com Quarkus e Java 21, projetado para gerenciar alertas, áreas de risco, ocorrências e usuários. O backend oferece um conjunto completo de endpoints CRUD para essas entidades, suportando operações de criação, recuperação, atualização, exclusão e autenticação de usuários (login/logout).

A aplicação frontend está hospedada separadamente e disponível em [Frontend Alerta360](https://frontgs.vercel.app/).

## Backend

### Tecnologias Utilizadas

- Java 21
- Framework Quarkus
- Jakarta RESTful Web Services (JAX-RS)
- Hibernate ORM com Panache
- Banco de Dados Oracle (via JDBC)
- Maven para build e gerenciamento de dependências
- JUnit 5 e Rest-Assured para testes

### Funcionalidades

- Endpoints REST para gerenciamento de:
  - Alertas (`/alertas`)
  - Áreas de Risco (`/areas-risco`)
  - Ocorrências (`/ocorrencias`)
  - Usuários (`/usuarios`)
- Autenticação de usuários com endpoints de login e logout, utilizando cookies seguros HttpOnly para gerenciamento de sessão.
- Operações CRUD completas para todas as entidades.
- Requisições e respostas em formato JSON utilizando Jackson.

### Resumo dos Endpoints REST

| Recurso        | Métodos HTTP           | Descrição                      |
|----------------|-----------------------|-------------------------------|
| `/alertas`     | GET, POST, PUT, DELETE| Gerenciamento de alertas       |
| `/areas-risco` | GET, POST, PUT, DELETE| Gerenciamento de áreas de risco|
| `/ocorrencias` | GET, POST, PUT, DELETE| Gerenciamento de ocorrências   |
| `/usuarios`    | GET, POST, PUT, DELETE| Gerenciamento de usuários      |
| `/usuarios/cadastro`| POST | Cadastro de usuários          ||
| `/usuarios/login`  | POST                | Autenticação de usuário (login)|
| `/usuarios/logout` | POST                | Logout do usuário              |

### Como Construir e Executar o Backend

#### Pré-requisitos

- Java 21 instalado
- Maven instalado
- Banco de dados Oracle configurado e acessível

#### Execução com Maven

```bash
# Executar o projeto
./mvnw quarkus:dev
```

## Frontend

O frontend da aplicação está hospedado no Vercel e pode ser acessado pelo link:

[https://frontgs.vercel.app/](https://frontgs.vercel.app/)

---

## Desenvolvedores

Este projeto foi desenvolvido por Yasmin Silva|560039, Lucas Borges|560027 e Pedro Silva|560393 como parte do desafio Global Solution da FIAP, focado na criação de soluções tecnológicas inovadoras para prevenção e apoio em situações de desastres naturais.

## Contato

Para dúvidas ou contribuições, entre em contato com a equipe de desenvolvimento.
