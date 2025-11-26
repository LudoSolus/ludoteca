# 🎲 Ludoteca (Frontend)

Sistema de gerenciamento de empréstimos de jogos de tabuleiro, eventos e usuários. Desenvolvido com **SvelteKit**, **TypeScript** e **TailwindCSS**, integrando-se com uma API Spring Boot.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Instalação e Execução](#instalação-e-execução)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Arquitetura](#arquitetura)
- [Rotas da Aplicação](#rotas-da-aplicação)
- [Configuração](#configuração)

---

## 🎯 Sobre o Projeto

A **Ludoteca** é uma aplicação web para gestão de bibliotecas de jogos de tabuleiro, permitindo:

- 📚 Cadastro e gerenciamento de jogos de tabuleiro
- 📅 Criação e controle de eventos
- 👥 Administração de usuários e instituições educacionais
- 🔄 Sistema de empréstimos e histórico
- 🔐 Autenticação e controle de acesso por perfis (Admin/Usuário)

---

## 🚀 Tecnologias Utilizadas

### Core
- **[SvelteKit](https://kit.svelte.dev/)** - Framework fullstack para Svelte
- **[TypeScript](https://www.typescriptlang.org/)** - Tipagem estática
- **[Vite](https://vitejs.dev/)** - Build tool e dev server

### Estilização
- **[TailwindCSS](https://tailwindcss.com/)** - Framework CSS utility-first
- **[@tailwindcss/forms](https://github.com/tailwindlabs/tailwindcss-forms)** - Estilos para formulários
- **[@tailwindcss/typography](https://tailwindcss.com/docs/typography-plugin)** - Tipografia responsiva

### Bibliotecas
- **[Axios](https://axios-http.com/)** - Cliente HTTP para comunicação com API
- **[RxJS](https://rxjs.dev/)** - Programação reativa
- **[Svelte FA](https://github.com/Cweili/svelte-fa)** - Ícones Font Awesome
- **[Maska](https://beholdr.github.io/maska/)** - Máscaras de input
- **[jwt-decode](https://github.com/auth0/jwt-decode)** - Decodificação de tokens JWT

### Desenvolvimento
- **[Prettier](https://prettier.io/)** + plugins - Formatação de código
- **[svelte-check](https://github.com/sveltejs/language-tools)** - Verificação de tipos

---

## 💻 Instalação e Execução

### Pré-requisitos

- Node.js (versão 18 ou superior)
- npm ou yarn

### Passos

1. **Clone o repositório**
```bash
git clone https://github.com/LudoSolus/ludoteca.git
cd frontend/web
```

2. **Instale as dependências**
```bash
npm install
```

3. **Configure as variáveis de ambiente**

Crie um arquivo `.env` na raiz do projeto:

```env
PUBLIC_API_URL=http://127.0.0.1:1234
```

4. **Inicie o servidor de desenvolvimento**
```bash
npm run dev
```

A aplicação estará disponível em `http://localhost:5173`

**Opções avançadas de desenvolvimento:**

Para customizar host e porta durante o desenvolvimento:

```bash
# Usar uma porta específica
npm run dev -- --port 8000

# Expor o servidor na rede local (acessível de outros dispositivos)
npm run dev -- --host 0.0.0.0 --port 3000
```

- `--port <número>`: Define uma porta customizada (útil quando 5173 está ocupada)
- `--host 0.0.0.0`: Permite acesso via IP local (ex: `192.168.1.10:5173`)
  - Útil para testar em dispositivos móveis na mesma rede
  - Útil para ambientes de desenvolvimento em containers/VMs

5. **Build para produção**
```bash
npm run build
```

Para visualizar o build localmente:
```bash
npm run preview
```

---

## 📂 Estrutura do Projeto

```
ludoteca-frontend/
├── src/
│   ├── lib/                          # Núcleo da aplicação
│   │   ├── api/                      # Camada de comunicação
│   │   │   ├── commands/             # Operações de escrita (CQRS)
│   │   │   │   ├── login-user/
│   │   │   │   └── register-user/
│   │   │   └── queries/              # Operações de leitura (CQRS)
│   │   │       ├── get-event-details/
│   │   │       └── list-educational-institutions/
│   │   │
│   │   ├── components/               # Atomic Design
│   │   │   ├── atoms/                # Componentes básicos (botões, inputs, ícones)
│   │   │   │   └── icons/
│   │   │   ├── molecules/            # Combinações de átomos (formulários, cards)
│   │   │   │   └── forms/
│   │   │   ├── organisms/            # Seções complexas (headers, sidebars)
│   │   │   └── templates/            # Layouts de página
│   │   │
│   │   ├── shared/                   # Recursos compartilhados
│   │   │   ├── enums/                # Enumeradores
│   │   │   ├── handlers/             # Handlers genéricos
│   │   │   │   ├── command/
│   │   │   │   └── query/
│   │   │   ├── helpers/              # Funções auxiliares
│   │   │   ├── hooks/                # Custom hooks
│   │   │   ├── interfaces/           # Interfaces TypeScript
│   │   │   └── stores/               # Stores Svelte (estado global)
│   │   │
│   │   └── assets/                   # Recursos estáticos (imagens, fontes)
│   │
│   └── routes/                       # Roteamento (file-based routing)
│       ├── admin/                    # Área administrativa
│       │   ├── board-games/          # Gestão de jogos
│       │   ├── events/               # Gestão de eventos
│       │   └── users/                # Gestão de usuários
│       │
│       ├── auth/                     # Autenticação
│       │   ├── login/
│       │   └── register/
│       │
│       ├── user/                     # Área do usuário
│       │   ├── board-game/[id]/      # Detalhes do jogo
│       │   ├── event/[id]/           # Detalhes do evento
│       │   ├── history/              # Histórico de empréstimos
│       │   ├── home/                 # Dashboard do usuário
│       │   └── profile/              # Perfil do usuário
│       │
│       ├── components/               # Página de demonstração de componentes
│       └── [...catchall]/            # Rota 404
│
├── static/                           # Arquivos públicos
│   └── images/
│
├── .svelte-kit/                      # Arquivos gerados (não versionado)
├── node_modules/                     # Dependências (não versionado)
├── package.json
├── svelte.config.js
├── tailwind.config.js
├── tsconfig.json
└── vite.config.ts
```

---

## 🏗️ Arquitetura

### Padrões Adotados

#### 1. **Atomic Design** (Componentes)
Organização hierárquica dos componentes visuais:

- **Atoms**: Elementos básicos e indivisíveis (Button, Input, Icon)
- **Molecules**: Combinações simples de átomos (SearchBar, FormField)
- **Organisms**: Estruturas complexas (Header, Sidebar, DataTable)
- **Templates**: Layouts de página reutilizáveis

#### 2. **CQRS Pattern** (API Layer)
Separação clara entre operações de leitura e escrita:

- **Commands**: Operações que modificam dados (POST, PUT, DELETE)
  - `login-user`, `register-user`, `create-board-game`
  
- **Queries**: Operações que apenas leem dados (GET)
  - `get-event-details`, `list-educational-institutions`, `get-board-game-by-id`

#### 3. **File-based Routing** (SvelteKit)
O roteamento é automático baseado na estrutura de pastas em `src/routes/`:

```
routes/
├── admin/
│   └── users/
│       └── +page.svelte          → /admin/users
├── user/
│   └── board-game/
│       └── [id]/
│           └── +page.svelte      → /user/board-game/123
```

### Fluxo de Dados

```
Componente UI
    ↓
Store/State (Svelte)
    ↓
Command/Query Handler
    ↓
API Layer (Axios)
    ↓
Backend Spring Boot
```

---

## 🗺️ Rotas da Aplicação

### Públicas (Não autenticadas)
- `/auth/login` - Página de login
- `/auth/register` - Página de cadastro

### Área do Usuário (Autenticação obrigatória)
- `/user/home` - Dashboard principal
- `/user/profile` - Perfil do usuário
- `/user/history` - Histórico de empréstimos
- `/user/board-game/[id]` - Detalhes de um jogo específico
- `/user/event/[id]` - Detalhes de um evento específico

### Área Administrativa (Perfil Admin)
- `/admin/board-games` - Gerenciar jogos de tabuleiro
- `/admin/events` - Gerenciar eventos
- `/admin/users` - Gerenciar usuários e permissões

### Outras
- `/components` - Página de demonstração de componentes (Desenvolvimento)
- `*` (catchall) - Página 404 (rota não encontrada)

---

## ⚙️ Configuração

### Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis:

```env
# URL da API Backend
PUBLIC_API_URL=http://127.0.0.1:1234

# Outras configurações (se necessário)
# PUBLIC_APP_NAME=Ludoteca
# PUBLIC_ENV=development
```

### Scripts Disponíveis

```bash
# Desenvolvimento
npm run dev              # Inicia servidor de desenvolvimento

# Build
npm run build            # Cria build de produção
npm run preview          # Visualiza build localmente

# Qualidade de código
npm run check            # Verifica tipos TypeScript
npm run format           # Formata código com Prettier
npm run lint             # (se configurado) Lint do código
```

---

## 🔐 Autenticação

A aplicação utiliza **JWT (JSON Web Tokens)** para autenticação:

1. Login via `/auth/login` retorna um token JWT
2. Token é armazenado no cliente (localStorage/cookies)
3. Requisições à API incluem o token no header `Authorization: Bearer <token>`
4. Backend valida o token e libera acesso conforme permissões

---
