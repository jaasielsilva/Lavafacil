# 🚗 Lavafácil

![Java](https://img.shields.io/badge/Java-21-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen?logo=spring)
![License](https://img.shields.io/github/license/jaasielsilva/Lavafacil)

## 📌 Descrição

Lavafácil é uma aplicação web desenvolvida em Java com Spring Boot que oferece um sistema completo para agendamento e gerenciamento de serviços de lavagem de veículos.  
Ela conta com uma interface moderna e responsiva usando Thymeleaf + Bootstrap, e integração com Google Sheets para registrar agendamentos automaticamente.

---

## 🚀 Tecnologias Utilizadas

- 💻 Java 21  
- 🌱 Spring Boot 3.4.5  
- 🐬 MySQL (produção)  
- 🧪 H2 (teste)  
- 🍃 Spring Data JPA  
- 🧩 Lombok  
- 🧪 Maven  
- 🎨 Thymeleaf  
- 🎯 Bootstrap  

---

## ✅ Funcionalidades

- ✅ Cadastro e gerenciamento de clientes  
- ✅ Cadastro e gerenciamento de veículos  
- ✅ Cadastro e gerenciamento de serviços  
- ✅ Sistema de agendamento integrado entre clientes, veículos e serviços  
- ✅ Interface web responsiva (mobile-first)  
- ✅ Integração com Google Sheets para registro dos agendamentos  

---

## 🛠️ Como Executar o Projeto

### 🔑 Pré-requisitos

- Java 21 instalado  
- Maven instalado  
- MySQL instalado e em execução (para uso em produção)  
- Conta de serviço no Google Cloud (para uso do Google Sheets)

---

### ⚙️ Configurações necessárias

#### 1. Banco de Dados

Você pode usar dois bancos de dados diferentes:

- **Banco H2 (Memória) para testes rápidos:**  
  O projeto já vem configurado para usar o banco H2 em memória por padrão.  
  Basta rodar a aplicação e ela criará as tabelas automaticamente.  
  Você pode acessar o console H2 em:  
  `http://localhost:8080/h2-console`  
  (Configure o JDBC URL como `jdbc:h2:mem:testdb`)

- **Banco MySQL para uso em produção:**  
  Crie um banco no MySQL:

  ```sql
  CREATE DATABASE lavafacil;
