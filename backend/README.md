# 🎲 Projeto Ludoteca — Backend

Este projeto é o **backend** do sistema **Ludoteca**, desenvolvido em **Java 25** com **Spring Boot 3.5.6**.

Ele tem como objetivo gerenciar empréstimos de jogos e atividades lúdicas, integrando autenticação JWT e controle de instituições educacionais.

---

## 🚀 Tecnologias Utilizadas

* **Java 25**
* **Spring Boot 3.5.6**
* **Maven 3.9.9**
* **PostgreSQL**
* **JWT (Json Web Token)**
* **Spring Security**
* **Spring Data JPA**
* **Springdoc OpenAPI (Swagger UI)**

---

## ⚙️ Configuração do Ambiente

### 1. 🧩 Instalar o Java 25

Baixe o **JDK 25** do site oficial da Oracle:
[Oracle JDK Downloads](https://www.oracle.com/java/technologies/downloads/)

Durante a instalação:
> Anote o caminho onde o JDK foi instalado (exemplo: `C:\Program Files\Java\jdk-25`)

#### ➕ Configurar variável de ambiente `JAVA_HOME`

1.  Abra o menu **Iniciar** → pesquise por **Editar variáveis de ambiente do sistema**.
2.  Clique em **Variáveis de Ambiente**.
3.  Em **Variáveis do Sistema**, clique em **Novo...** e adicione:
    * **Nome da variável:** `JAVA_HOME`
    * **Valor da variável:** `C:\Program Files\Java\jdk-25`
4.  Localize a variável **`Path`**, clique em **Editar** e adicione:
    `%JAVA_HOME%\bin`
5.  Salve tudo e feche.

✅ **Verificar instalação**
Abra o terminal e digite:
`java -version`

---

## 🏃 Como Executar

### 1. Clonar o repositório

`git clone https://github.com/seu-usuario/ludoteca-backend.git`
`cd ludoteca-backend`

### 2. Configurar Banco de Dados

1.  Instale e inicie o **PostgreSQL**.
2.  Crie um banco de dados vazio chamado `ludoteca_db`.
3.  Abra o arquivo `src/main/resources/application.properties` (ou `.yml`) e atualize as credenciais:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/ludoteca_db
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
    ```

### 3. Executar o Projeto com Maven

Abra o terminal na pasta raiz do projeto (`ludoteca-backend`) e execute:

`mvn clean`
`mvn spring-boot:run`

✅ Se tudo der certo, o servidor iniciará na porta configurada (geralmente `1234` ou `8080`). Você verá algo como:
`Tomcat started on port 1234 (http)`

### 4. Acessar o Swagger (Documentação da API)

Abra seu navegador e acesse a documentação interativa gerada automaticamente:
[http://localhost:1234/swagger-ui.html](http://localhost:1234/swagger-ui.html)
