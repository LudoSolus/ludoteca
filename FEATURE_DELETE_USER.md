# Documentação - Exclusão Lógica de Usuários

## Overview

Esta funcionalidade permite que administradores removam usuários do sistema de forma segura, utilizando o padrão de **soft delete** (exclusão lógica). Os dados dos usuários são preservados no banco de dados com os campos `removed = true` e `deletedAt` preenchido com a data/hora da exclusão.

## Endpoint

**URL:** `DELETE /commands/admin/users/{id}`

**Método:** DELETE

**Autenticação:** Requerida (usuário autenticado deve ser ADMIN)

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Parâmetros:**
- `{id}` - UUID do usuário a ser removido (no path)

## Critérios de Aceitação Implementados

✅ Apenas administradores podem acessar a rota
✅ O sistema recebe o id do usuário via rota
✅ O sistema valida se o usuário existe
✅ O sistema valida se o usuário já não foi removido
✅ O sistema impede exclusão do próprio administrador autenticado
✅ O sistema impede exclusão de usuários com empréstimos ativos
✅ O sistema realiza exclusão lógica (removed = true)
✅ O sistema preenche deletedAt com data e hora da exclusão
✅ O sistema retorna mensagem de sucesso após exclusão
✅ O sistema retorna erro apropriado caso o usuário não exista
✅ O sistema retorna erro apropriado caso existam empréstimos ativos

## Exemplos de Uso

### Sucesso - Remover usuário válido

**Request:**
```bash
curl -X DELETE \
  http://localhost:1234/commands/admin/users/550e8400-e29b-41d4-a716-446655440000 \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json"
```

**Response (200 OK):**
```json
{
  "data": "Usuário removido com sucesso!"
}
```

### Erro - ID inválido

**Request:**
```bash
curl -X DELETE \
  http://localhost:1234/commands/admin/users/invalid-id \
  -H "Authorization: Bearer {token}"
```

**Response (400 Bad Request):**
```json
{
  "message": "Id de usuário inválido!",
  "errorCode": "USR_003"
}
```

### Erro - Usuário não encontrado

**Request:**
```bash
curl -X DELETE \
  http://localhost:1234/commands/admin/users/00000000-0000-0000-0000-000000000000 \
  -H "Authorization: Bearer {token}"
```

**Response (404 Not Found):**
```json
{
  "message": "Usuário não encontrado ou já foi removido.",
  "errorCode": "USR_002"
}
```

### Erro - Tentativa de deletar a si mesmo

**Request:**
```bash
curl -X DELETE \
  http://localhost:1234/commands/admin/users/550e8400-e29b-41d4-a716-446655440000 \
  -H "Authorization: Bearer {token_do_mesmo_usuario}"
```

**Response (400 Bad Request):**
```json
{
  "message": "Não é possível deletar sua própria conta.",
  "errorCode": "USR_004"
}
```

### Erro - Usuário com empréstimos ativos

**Request:**
```bash
curl -X DELETE \
  http://localhost:1234/commands/admin/users/550e8400-e29b-41d4-a716-446655440001 \
  -H "Authorization: Bearer {token}"
```

**Response (400 Bad Request):**
```json
{
  "message": "Não é possível remover um usuário com empréstimos ativos.",
  "errorCode": "USR_006"
}
```

## Arquivos Modificados

### 1. **DeleteUserCommand.java** (Novo)
- Record que encapsula o DTO com o ID do usuário a ser deletado
- Localização: `command/deleteUser/DeleteUserCommand.java`

### 2. **DeleteUserHandler.java** (Novo)
- Service que implementa toda a lógica de negócio
- Validações:
  - ID válido (UUID)
  - Usuário existe e não foi removido
  - Admin não tenta deletar a si mesmo
  - Usuário não possui empréstimos ativos (status BORROWED)
- Executa soft delete: `removed = true` e `deletedAt = LocalDateTime.now()`
- Localização: `command/deleteUser/DeleteUserHandler.java`

### 3. **LoanRepository.java** (Modificado)
- Adicionado método: `hasActiveLoansByUserId(UUID userId)`
- Query que verifica se existe algum empréstimo ativo (status = BORROWED) e não removido para um usuário
- Utiliza JPQL com enumeração `GameStatus.BORROWED`
- Localização: `common/repository/LoanRepository.java`

### 4. **UserAdminCommandController.java** (Modificado)
- Adicionado novo endpoint: `DELETE /{id}`
- Injeta `DeleteUserHandler`
- Recebe usuário autenticado via `@AuthenticationPrincipal`
- Retorna mensagem de sucesso ou erro
- Localização: `command/controller/adminAcess/UserAdminCommandController.java`

## Fluxo de Execução

```
1. Cliente faz DELETE request com ID do usuário
   ↓
2. Spring Security valida autenticação e ROLE ADMIN
   ↓
3. DeleteUserHandler.handle() é chamado
   ↓
4. Validar ID (formato UUID válido)
   ↓
5. Validar se admin tenta deletar a si mesmo
   ↓
6. Buscar usuário não removido
   ↓
7. Validar se já foi removido
   ↓
8. Validar se possui empréstimos ativos (status BORROWED)
   ↓
9. Marcar como removido: removed = true, deletedAt = agora
   ↓
10. Salvar no banco
   ↓
11. Retornar mensagem de sucesso
```

## Códigos de Erro

| Código | Descrição |
|--------|-----------|
| USR_002 | Usuário não encontrado ou já foi removido |
| USR_003 | ID de usuário inválido (não é UUID válida) |
| USR_004 | Não é possível deletar sua própria conta |
| USR_005 | Usuário já foi removido |
| USR_006 | Usuário possui empréstimos ativos |

## Considerações de Implementação

### Por que Soft Delete?

- **Integridade Referencial:** Mantém relacionamentos com empréstimos, eventos, participações
- **Auditoria:** Preserva histórico completo do sistema
- **Recuperação:** Dados podem ser recuperados se necessário
- **Conformidade:** Segue padrão já existente no projeto (outras entidades usam `removed` e `deletedAt`)

### Segurança

- ✅ Apenas ADMIN pode acessar (Spring Security)
- ✅ Admin não pode deletar sua própria conta
- ✅ Validação de empréstimos ativo antes de remover

### Performance

- Query JPA otimizada com `@Query` para verificar empréstimos
- Usa apenas `COUNT` ao invés de recuperar lista completa
- Operação única de update via soft delete

## Impacto em Outras Funcionalidades

Consultas existentes que usam `findUserNativeAndRemovedFalse()` continuarão funcionando normalmente, pois o método já filtra usuários removidos.

Listagens de usuários devem continuar usando este mesmo padrão para não exibir usuários deletados.

## Testes Recomendados

1. ✅ Deletar usuário válido sem empréstimos
2. ✅ Tentar deletar usuário inexistente
3. ✅ Tentar deletar a si mesmo (admin)
4. ✅ Tentar deletar usuário com empréstimo ativo
5. ✅ Tentar com ID inválido (não-UUID)
6. ✅ Verificar que deleted_at é preenchido corretamente
7. ✅ Verificar que usuário não aparece mais nas listagens
