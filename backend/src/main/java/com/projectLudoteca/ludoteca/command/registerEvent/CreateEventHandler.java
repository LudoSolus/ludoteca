package com.projectLudoteca.ludoteca.command.registerEvent;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.entity.Game;
import com.projectLudoteca.ludoteca.common.entity.GameEvent;
import com.projectLudoteca.ludoteca.common.enums.EventStatus;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.EventRepository;
import com.projectLudoteca.ludoteca.common.repository.GameEventRepository;
import com.projectLudoteca.ludoteca.common.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CreateEventHandler {

    private final EventRepository eventRepository;
    private final GameRepository gameRepository;
    private final GameEventRepository gameEventRepository;

    public CreateEventHandler(EventRepository eventRepository, GameRepository gameRepository, GameEventRepository gameEventRepository) {
        this.eventRepository = eventRepository;
        this.gameRepository = gameRepository;
        this.gameEventRepository = gameEventRepository;
    }

    public String handle(CreateEventCommand command) {
        if (command.name() == null || command.name().isBlank()
                || command.startDate() == null
                || command.finalDate() == null
                || command.street() == null
                || command.number() == null
                || command.neighborhood() == null
                || command.city() == null
                || command.state() == null
                || command.zipCode() == null) {
            throw new BusinessException("Campos obrigatórios não podem ser nulos.");
        }

        if (command.finalDate().isBefore(command.startDate())) {
            throw new BusinessException("A data final não pode ser antes da inicial.");
        }

        if (command.gamesIds() != null && !command.gamesIds().isEmpty()) {
            Set<UUID> unique = new HashSet<>(command.gamesIds());

            if (unique.size() != command.gamesIds().size()) {
                throw new BusinessException("A lista de jogos contém IDs duplicados.");
            }
        }

        List<Game> games;

        if (command.gamesIds() != null && !command.gamesIds().isEmpty()) {
            games = gameRepository.findAllById(command.gamesIds());

            if (games.size() != command.gamesIds().size()) {
                throw new BusinessException("Um ou mais IDs de jogos são inválidos.");
            }
        }

        Event event = new Event();
        event.setName(command.name());
        event.setDescription(command.description());
        event.setStartDate(command.startDate());
        event.setFinalDate(command.finalDate());
        event.setStatus(EventStatus.SCHEDULED);

        event.setStreet(command.street());
        event.setNumber(command.number());
        event.setSupplement(command.supplement());
        event.setNeighborhood(command.neighborhood());
        event.setCity(command.city());
        event.setState(command.state());
        event.setZipCode(command.zipCode());

        if ((command.hasBoardGames() == null || !command.hasBoardGames()) &&
                (command.hasRpg() == null || !command.hasRpg()) &&
                (command.hasEscapeRoom() == null || !command.hasEscapeRoom())) {

            throw new IllegalArgumentException(
                    "O evento deve possuir pelo menos uma modalidade: jogos de tabuleiro, RPG ou escape room."
            );
        }

        event.setHasBoardGames(command.hasBoardGames());
        event.setHasRpg(command.hasRpg());
        event.setHasEscapeRoom(command.hasEscapeRoom());

        eventRepository.save(event);

        if (command.gamesIds() != null && !command.gamesIds().isEmpty()) {
            games = gameRepository.findAllById(command.gamesIds());

            for (Game game : games) {
                GameEvent ge = new GameEvent(game, event);
                gameEventRepository.save(ge);
            }
        }

        return "Evento cadastrado com Sucesso!";
    }

}
