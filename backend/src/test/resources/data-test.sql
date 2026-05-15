-- Limpando a tabela para evitar lixo se o script rodar duas vezes
DELETE FROM game_event;
DELETE FROM game;

-- Inserindo um Jogo Estático
INSERT INTO game (id, title, description, category, min_players, max_players, is_available, removed)
VALUES (
        'c3b0c531-90fa-4091-a602-bb049e794301',
        'Zombicide',
        'Jogo de tabuleiro com zumbis',
        'COOPERATIVE',
        1,
        6,
        true,
        false
       );