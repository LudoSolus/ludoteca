# 🎲 Projeto Ludoteca — Backend

Este projeto é o **backend** do sistema **Ludoteca**, desenvolvido em **Java 25** com **Spring Boot 3.5.6**.

Ele tem como objetivo gerenciar empréstimos de jogos e atividades lúdicas, integrando autenticação JWT e controle de instituições educacionais.

---

## 🚀 Tecnologias Utilizadas

* **Java 25 (LTS)**
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

Baixe o **JDK 25** (versão LTS) do site oficial da Oracle:
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
    ```
    %JAVA_HOME%\bin
    ```
5.  Salve tudo e feche.

✅ **Verificar instalação**
```bash
java -version
