# 🎲 Ludoteca (Backend)

API RESTful para o sistema de gerenciamento de empréstimos de jogos de tabuleiro, eventos e usuários. Desenvolvido com **Java 25**, **Spring Boot 3.5.6**, **PostgreSQL** e arquitetura **CQRS**.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Instalação e Execução](#instalação-e-execução)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Arquitetura](#arquitetura)
- [Documentação da API](#documentação-da-api)
- [Configuração](#configuração)
- [Autenticação](#autenticação)

---

## 🎯 Sobre o Projeto

O backend da **Ludoteca** é o núcleo de regras de negócio e persistência de dados do sistema, fornecendo uma API robusta e segura para:

- 📚 Cadastro e gerenciamento do acervo de jogos de tabuleiro
- 📅 Gestão do ciclo de vida de eventos (agendamento, início, término)
- 👥 Administração de usuários (Admin/User) e instituições educacionais
- 🔄 Processamento de empréstimos, devoluções e validações lógicas
- 🔐 Emissão de tokens JWT e controle de autorização

---

## 🚀 Tecnologias Utilizadas

### Core
- **[Java 25](https://www.oracle.com/java/)** - Linguagem de programação
- **[Spring Boot](https://spring.io/projects/spring-boot)** - Framework principal
- **[Maven](https://maven.apache.org/)** - Gerenciador de dependências e build

### Banco de Dados
- **[PostgreSQL](https://www.postgresql.org/)** - Banco de dados relacional
- **Spring Data JPA** - Abstração de persistência e repositórios
- **Hibernate** - ORM (Object-Relational Mapping)

### Segurança e Documentação
- **Spring Security** - Controle de acesso e filtros de segurança
- **[JJWT](https://github.com/jwtk/jjwt)** - Geração e validação de JSON Web Tokens
- **[Springdoc OpenAPI](https://springdoc.org/)** - Geração automática do Swagger UI

### Desenvolvimento
- **[Lombok](https://projectlombok.org/)** - Redução de código boilerplate
- **Spring Boot DevTools** - Hot-reload durante o desenvolvimento

---

## 💻 Instalação e Execução

### Pré-requisitos

- Java JDK 25
- Maven 3.9+
- PostgreSQL 12+

Aqui está a continuação do seu `README.md`, começando exatamente da seção **Passos** até o final do documento.

Basta clicar em "Copiar código" no canto superior direito do bloco abaixo e colar no seu arquivo:

### Passos

1. **Clone o repositório**
```bash
git clone [https://github.com/LudoSolus/ludoteca.git](https://github.com/LudoSolus/ludoteca.git)
cd backend

```

2. **Configure o Banco de Dados**
Certifique-se de que o PostgreSQL está rodando e crie um banco de dados vazio:

```sql
CREATE DATABASE ludoteca_db;

```

3. **Configure as variáveis de ambiente**
Abra o arquivo `src/main/resources/application.properties` e atualize as credenciais:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ludoteca_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

```

4. **Instale as dependências e limpe o projeto**

```bash
mvn clean install

```

5. **Inicie o servidor de desenvolvimento**

```bash
mvn spring-boot:run

```

A API estará disponível em `http://localhost:1234` (ou a porta configurada no seu `.properties`).

---

## 📂 Estrutura do Projeto

```text
ludoteca-backend/
├── src/main/java/com/projectLudoteca/ludoteca/
│   ├── command/                  # Operações de ESCRITA (CQRS)
│   │   ├── controller/           # Endpoints de mutação (POST, PUT, DELETE)
│   │   │   ├── adminAcess/
│   │   │   └── userAcess/
│   │   ├── registerGame/         # Use Cases isolados (Handlers)
│   │   ├── updateEvent/
│   │   └── ...
│   │
│   ├── query/                    # Operações de LEITURA (CQRS)
│   │   ├── controller/           # Endpoints de leitura (GET)
│   │   │   ├── adminAcess/
│   │   │   └── userAcess/
│   │   ├── listAllEventsForAdmin/
│   │   ├── dashboard/
│   │   └── ...
│   │
│   ├── common/                   # Recursos Compartilhados
│   │   ├── entity/               # Entidades JPA (Domínio)
│   │   ├── enums/                # Enumerators
│   │   ├── exception/            # Tratamento global de erros
│   │   ├── repository/           # Interfaces Spring Data JPA
│   │   ├── response/             # Padrão de respostas (ApiResponse)
│   │   └── validation/           # Validadores customizados
│   │
│   └── infrastructure/           # Configurações de Infra
│       └── security/
│           └── config/           # JwtAuthFilter, SecurityConfig, CORS
│
├── src/main/resources/
│   └── application.properties    # Configurações do Spring Boot
│
└── pom.xml                       # Dependências do Maven

```

---

## 🏗️ Arquitetura

### Padrões Adotados

#### 1. **CQRS Pattern** (Command Query Responsibility Segregation)

Separação física entre operações de leitura e escrita:

* **Commands**: Lidam com ações que alteram o estado do sistema (criar usuário, fazer empréstimo). Possuem regras de negócio complexas.
* **Queries**: Lidam estritamente com a recuperação de dados (listar jogos, ver dashboard). São otimizadas para leitura.

#### 2. **Single Responsibility Principle** (Handlers)

O projeto utiliza o padrão **Command/Query Handler**. Cada operação de negócio (ex: `CreateGameHandler`) tem sua própria classe dedicada, substituindo services globais gigantes e facilitando os testes.

### Fluxo de Dados

```text
Requisição HTTP (JSON)
    ↓
Controller (Validações estruturais @Valid)
    ↓
Command/Query Record (DTO de transporte)
    ↓
Handler (Validações de negócio)
    ↓
Repository (Spring Data JPA)
    ↓
Banco de Dados (PostgreSQL)

```

---

## 🗺️ Documentação da API

A documentação interativa de todas as rotas e *payloads* esperados é gerada automaticamente pelo **Swagger UI**.

Com a aplicação rodando, acesse no navegador:
👉 **[http://localhost:1234/swagger-ui.html](https://www.google.com/search?q=http://localhost:1234/swagger-ui.html)**

### Resumo das Áreas da API

* `/commands/users/` - Autenticação e gestão da própria conta (User)
* `/commands/admin/` - Gestão de eventos, jogos e empréstimos (Admin)
* `/queries/users/` - Consultas públicas e de usuário
* `/queries/admin/` - Relatórios e listagens administrativas

---

## ⚙️ Configuração

### Variáveis de Ambiente e Properties

As configurações principais encontram-se em `application.properties`:

```properties
# Porta do servidor
server.port=1234

# Hibernate / JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Secret
jwt.secret=sua_chave_secreta_aqui
jwt.expiration=86400000

```

### Scripts Maven Disponíveis

```bash
mvn clean         # Limpa o build anterior
mvn compile       # Compila o código fonte
mvn test          # Executa os testes automatizados
mvn package       # Empacota a aplicação gerando o arquivo .jar

```

---

## 🔐 Autenticação

A aplicação utiliza **JWT (JSON Web Tokens)** atuando em modo *Stateless*:

1. O endpoint de `/login` valida as credenciais e retorna um token JWT.
2. O token contém o ID do usuário e suas *Roles* (ex: `USER`, `ADMIN`).
3. O Frontend deve enviar este token no header de requisições protegidas: `Authorization: Bearer <token>`.
4. O `JwtAuthFilter` do Spring Security intercepta, valida a assinatura e autoriza o acesso conforme o perfil da rota.

```

```
