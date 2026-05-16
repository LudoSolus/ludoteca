# Detalhes do Projeto da Disciplina Oficina de Integração 2

## **Planejamento:**

### **Requisitos Funcionais:**

#### **Módulo 1: Autenticação e Usuário**

* **RF01 \- Login:** Autenticação de usuários e administradores via credenciais seguras. 

* **RF02 \- Logout:** Encerramento seguro da sessão ativa no sistema. 

* **RF03 \- Cadastro:** Registro de novos usuários interessados no projeto de extensão. 

* **RF04 \- Recuperar Senha:** Fluxo de redefinição de acesso via e-mail ou tokens de segurança. 

* **RF05 \- Editar Dados do Perfil:** Permite a atualização de informações cadastrais pelo próprio usuário. 

#### **Módulo 2: Gestão de Eventos**

* **RF06 \- Listar Eventos:** Exibição cronológica e filtrável de todos os eventos registrados.  

* **RF07 \- Iniciar Evento:** Transição de estado de um evento para "Ativo", permitindo transações.  

* **RF08 \- Finalizar Evento:** Encerramento oficial das atividades e consolidação de dados.  

* **RF09 \- Emprestar Jogo:** Registro transacional da saída de um item do acervo para um usuário.  

* **RF10 \- Devolver Jogo:** Baixa no empréstimo e retorno do item à disponibilidade do acervo. 

* **RF11 \- Criar Evento:** Definição de novos eventos (nome, data, local e responsáveis). 

* **RF12 \- Editar Evento:** Alteração de metadados de eventos ainda não finalizados. 

* **RF13 \- Excluir Evento:** Remoção do registro do sistema através de deleção lógica (**soft delete**). 

* **RF14 \- Copiar Evento:** Funcionalidade de clonagem de parâmetros para agilizar a criação de eventos recorrentes.  

#### **Módulo 3: Gestão do Acervo (Jogos)**

* **RF15 \- Listar Jogos:** Catálogo completo com visualização de estado e categoria.   

* **RF16 \- Buscar Jogo:** Filtro dinâmico por nome, editora, gênero ou complexidade.   

* **RF17 \- Criar Jogo:** Adição de novos títulos ao inventário da Ludoteca.   

* **RF18 \- Editar Jogo:** Manutenção das informações técnicas e descritivas dos títulos.   

* **RF19 \- Excluir Jogo:** Retirada definitiva ou lógica de um item do catálogo.  

* **RF20 \- Leitura de Código de Barras:** Integração com hardware para identificação e busca instantânea de itens.  

#### **Módulo 4: Administração de Usuários** 

* **RF21 \- Listar Usuários:** Visualização da base de dados de frequentadores comuns.    

* **RF22 \- Listar Administradores:** Gestão dos perfis com permissões de gestão do sistema.    

* **RF23 \- Criar Usuário/Adm:** Cadastro direto realizado pela equipe administrativa.    

* **RF24 \- Editar Usuário:** Modificação de níveis de acesso e dados de terceiros.    

* **RF25 \- Excluir Usuário:** Inativação de perfis da base de dados.   

* **RF26 \- Buscar Usuário/Adm:** Pesquisa global por nome, CPF ou e-mail.   

#### **Módulo 5: Domínio de Escape Room**  

* **RF27 \- Criar Escape Room:** Registro de salas de desafio e seus parâmetros específicos.     

* **RF28 \- Editar Escape Room:** Atualização de dificuldades, temas e capacidades das salas. 

* **RF29 \- Remover Escape Room:** Exclusão de salas da grade de atividades.     

* **RF30 \- Listar Escape Rooms:** Painel de visualização de todos os cenários disponíveis.     

* **RF31 \- Criar Sessões (1:N):** Instanciação de horários vinculados a uma sala específica.    

* **RF32 \- Abrir Sessões:** Ativação de um horário para recebimento de inscrições. 

* **RF33 \- Editar Sessão:** Ajuste de horários ou limites de participantes de uma instância. 

* **RF34 \- Remover Sessão:** Cancelamento de um horário específico de desafio. 

* **RF35 \- Relacionar Participantes (N:N):** Associação de múltiplos usuários a uma sessão única. 

* **RF36 \- Remover Participantes:** Desvinculação de usuários de uma sessão agendada. 

* **RF37 \- Iniciar Sessão:** Disparo do cronômetro e registro do início da atividade. 

* **RF38 \- Finalizar Sessão:** Registro de desfecho (sucesso/falha) e tempo de conclusão. 

#### **Módulo 6: Dashboard e Relatórios** 

* **RF39 \- Dashboard e Relatórios:** Geração de indicadores visuais e documentos (*PDF/Excel*) abrangendo usuários, eventos, jogos e desempenho do Escape Room.

### **Funcionalidades Pendentes:**

* **RF11** \- Criar Evento  
* **RF12** \- Editar Evento  
* **RF13** \- Excluir Evento  
* **RF14** \- Copiar Evento  
* **RF19** \- Excluir Jogo  
* **RF20** \- Código de Barras  
* **RF25** \- Excluir Usuário  
* **RF27** \- Criar Escape Room  
* **RF28** \- Editar Escape Room  
* **RF29** \- Remover Escape Room  
* **RF30** \- Listar Escape Rooms  
* **RF31** \- Criar Sessões  
* **RF32** \- Abrir Sessões  
* **RF33** \- Editar Sessão  
* **RF34** \- Remover Sessão  
* **RF35** \- Relacionar Participantes  
* **RF36** \- Remover Participantes  
* **RF37** \- Iniciar Sessão  
* **RF38** \- Finalizar Sessão  
* **RF39** \- Dashboard e Relatórios

### **Arquitetura de Alto Nível:**

O sistema adota uma arquitetura de sistemas distribuídos baseada no padrão **Client-Server** com desacoplamento total entre as camadas: 

* **Frontend (Camada de Apresentação):** Desenvolvido em **Svelte** com **TypeScript**, focado em reatividade e SPA (*Single Page Application*). 

* **Backend (Camada de Serviço):** Baseado em **Spring Boot (Java)**, seguindo o padrão RESTful. Utiliza Spring Security para a camada de autenticação ou autorização via JWT. 

* **Persistência (Camada de Dados):** Banco de dados relacional **PostgreSQL**, gerenciado via Spring Data JPA para garantir a integridade referencial, muito necessária no módulo de Escape Room. 

* **Comunicação:** JSON via protocolo HTTPS para todas as trocas de dados. 

### **Estratégia de Automação de Testes:**

* **Testes Unitários:** Focado no backend utilizando **JUnit 5** e **Mockito** para isolar dependências. 

* **Métrica de Cobertura:** Utilização do plugin **JaCoCo** para monitorar a cobertura de código, visando manter um índice mínimo de 70% nas classes de lógica de negócio (será desconsiderado até o código legado ter sido testado). 

* **Integração Contínua (CI):** Automação via **GitHub Actions**, onde cada *Pull Request* dispara um build e a execução de toda a suíte de testes. A falha nos testes impede o *merge* para a branch principal.

### **Tecnologias e Ferramentas:**

* **Linguagens:** Java 25, TypeScript 5.0.0.

* **Frameworks:** Spring Boot 3.5.6, Svelte 5.45.2. 

* **Infraestrutura:** PostgreSQL, GitHub Actions. 

* **Gestão:** GitHub Projects (Kanban), Swagger (Documentação da API). 

### **Cronograma de Execução (Sprints):**

#### **Sprint 1:**

* **Fase 1 (Semanas 6-7):** Foco na organização do projeto, reestruturação e divisão dos trabalhos entre a equipe .   
* **Fase 2 (Semana 8-9):** Foco em Infraestrutura de CI, Finalização de Eventos do CRUD de eventos, funcionalidade de copiar evento, finalização do CRUD de usuários, finalização do CRUD de jogos, Dashboard e Leitor de Código de Barras. 

	  
