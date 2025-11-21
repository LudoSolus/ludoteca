package com.projectLudoteca.ludoteca.command.updateEvent;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.entity.Game;
import com.projectLudoteca.ludoteca.common.entity.GameEvent;
import com.projectLudoteca.ludoteca.common.enums.EventStatus;
import com.projectLudoteca.ludoteca.common.repository.EventRepository;
import com.projectLudoteca.ludoteca.common.repository.GameEventRepository;
import com.projectLudoteca.ludoteca.common.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UpdateEventHandler {

    private final EventRepository eventRepository;
    private final GameRepository gameRepository;
    private final GameEventRepository gameEventRepository;

    public UpdateEventHandler(EventRepository eventRepository, GameRepository gameRepository, GameEventRepository gameEventRepository) {
        this.eventRepository = eventRepository;
        this.gameRepository = gameRepository;
        this.gameEventRepository = gameEventRepository;
    }

    @Transactional
    public String handle(UUID id, UpdateEventCommand command) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado."));

        if (event.getStatus() == EventStatus.COMPLETED) {
            throw new RuntimeException("Este evento já foi finalizado e não pode ser editado.");
        }

        boolean noFieldSent =
                command.name() == null &&
                        command.description() == null &&
                        command.startDate() == null &&
                        command.finalDate() == null &&
                        command.street() == null &&
                        command.number() == null &&
                        command.supplement() == null &&
                        command.neighborhood() == null &&
                        command.city() == null &&
                        command.state() == null &&
                        command.zipCode() == null &&
                        command.hasBoardgames() == null &&
                        command.hasRpg() == null &&
                        command.hasEscapeRoom() == null &&
                        command.gamesIds() == null;

        if (noFieldSent) {
            throw new RuntimeException("Nenhum dado foi informado. Envie ao menos um campo para atualizar o evento.");
        }

        if (command.name() != null)
            event.setName(command.name());

        if (command.description() != null)
            event.setDescription(command.description());

        if (command.startDate() != null)
            event.setStartDate(command.startDate());

        if (command.finalDate() != null)
            event.setFinalDate(command.finalDate());

        // Endereço
        if (command.street() != null) event.setStreet(command.street());
        if (command.number() != null) event.setNumber(command.number());
        if (command.supplement() != null) event.setSupplement(command.supplement());
        if (command.neighborhood() != null) event.setNeighborhood(command.neighborhood());
        if (command.city() != null) event.setCity(command.city());
        if (command.state() != null) event.setState(command.state());
        if (command.zipCode() != null) event.setZipCode(command.zipCode());

//        if (command.hasBoardgames() != null)
//            event.setHasBoardgames(command.hasBoardgames());
//
//        if (command.hasRpg() != null)
//            event.setHasRpg(command.hasRpg());
//
//        if (command.hasEscapeRoom() != null)
//            event.setHasEscapeRoom(command.hasEscapeRoom());

        eventRepository.save(event);

        if (command.gamesIds() != null) {

            gameEventRepository.deleteByEvent(event);

            if (!command.gamesIds().isEmpty()) {

                List<Game> games = gameRepository.findAllById(command.gamesIds());

                if (games.size() != command.gamesIds().size()) {
                    throw new RuntimeException("Um ou mais IDs de jogos são inválidos.");
                }

                long distinctCount = command.gamesIds().stream().distinct().count();

                if (distinctCount != command.gamesIds().size()) {
                    throw new RuntimeException("IDs de jogos duplicados não são permitidos.");
                }

                for (Game game : games) {
                    GameEvent ge = new GameEvent(game, event);
                    gameEventRepository.save(ge);
                }
            }
        }

        return "Atualização realizada com sucesso!";
    }

}
