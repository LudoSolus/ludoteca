## Mapa das Rotas

/auth  
--/register  
--/login  
--/reset-password 

/home  
/profile  
/history  
/event/:id  
/board-game/:id  

/admin  
--/users  
----/:id  
------/edit  
--/events  
----/:id  
------/edit  
--/board-games  
----/:id  
------/edit  
--/rpg  
----/:id  
------/edit  
------/characters  
--------/:id  
--------/edit  
--/escape-room  
----/:id  
------/edit  


## Rotas

### Registro
Rota: /auth/register  
Rota para se cadastrar na plataforma.

### Login
Rota: /auth/login  
Rota para se fazer login na plataforma.

### Esqueci minha senha
Rota: /auth/reset-password   
Rota para trocar sua senha quando ela foi esquecida.

### Home
Rota: /home   
Tela padrão do sistema, mostra os próximos eventos e as campanhas de RPG abertas.

### Perfil
Rota: /profile  
Tela para o usuário ver seu perfil e conseguir editar seus dados.

### Histórico
Rota: /history   
Tela para o usuário ver seu histórico e os seus dados estatísticos na plataforma.

### Evento
Rota: /event/:id   
Tela para usuários comuns verem detalhes do evento.

### Jogo de tabuleiro
Rota: /board-game/:id   
Tela para usuários comuns verem detalhes do jogo de tabuleiro.

### Administração
Rota: /admin  
Tela que mostra alguns gráficos e dashboards para os administradores do Ludico.

### Administração - usuários
Rota: /admin/users   
Tela que mostra a lista com todos os usuários cadastrados, incluindo os adminitradores.

### Administração - usuário
Rota: /admin/user/:id  
Tela que mostra os detalhes de um usuário.

### Administração - usuário editar
Rota: /admin/user/:id/edit  
Tela que permite aos ADMs editarem os dados de um usuário.
*Verificar se os ADMs vão poder editar os usuários*

### Administração - eventos
Rota: /admin/events   
Tela que mostra a lista com todos os eventos cadastrados.

### Administração - evento
Rota: /admin/events/:id  
Tela que mostra os detalhes do evento.

### Administração - evento editar
Rota: /admin/events/:id/edit  
Tela que permite aos ADMs editarem os dados do evento.

### Administração - jogos de tabuleiro
Rota: /admin/board-games    
Tela que mostra a lista com todos os jogos de tabuleiro cadastrados.

### Administração - jogo de tabuleiro
Rota: /admin/board-games/:id  
Tela que mostra os detalhes do jogo de tabuleiro.

### Administração - jogo de tabuleiro editar
Rota: /admin/board-games/:id/edit  
Tela que permite aos ADMs editarem os dados do jogo de tabuleiro.

### Administração - RPGs
Rota: /admin/rpgs   
Tela que mostra a lista com todos os rpgs cadastrados.

### Administração - RPG
Rota: /admin/rpgs/:id  
Tela que mostra os detalhes do rpg.

### Administração - RPG editar
Rota: /admin/rpgs/:id/edit  
Tela que permite aos ADMs editarem os dados do rpg.

### Administração - RPG personagens
Rota: /admin/rpgs/:id/characters  
Tela que permite ver a lista de personagens do rpg.

### Administração - RPG personagem
Rota: /admin/rpgs/:id/characters/:id  
Tela que permite ver um personagem do rpg.

### Administração - RPG personagem editar
Rota: /admin/rpgs/:id/characters/:id/edit  
Tela que permite aos ADMs editarem os dados de um personagem do rpg.

### Administração - escape-room
Rota: /admin/escape-room   
Tela que mostra a lista com todos os escape-room cadastrados.

### Administração - escape-room
Rota: /admin/escape-room/:id  
Tela que mostra os detalhes do escape-room.

### Administração - escape-room editar
Rota: /admin/escape-room/:id/edit  
Tela que permite aos ADMs editarem os dados do escape-room.
