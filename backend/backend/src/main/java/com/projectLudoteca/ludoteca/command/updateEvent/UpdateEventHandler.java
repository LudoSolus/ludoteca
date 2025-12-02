package com.projectLudoteca.ludoteca.command.updateEvent;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.entity.Game;
import com.projectLudoteca.ludoteca.common.entity.GameEvent;
import com.projectLudoteca.ludoteca.common.enums.EventStatus;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.EventRepository;
import com.projectLudoteca.ludoteca.common.repository.GameEventRepository;
import com.projectLudoteca.ludoteca.common.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public String handle(String id, UpdateEventCommand command) {

        UUID eventId;

        try{
            eventId = UUID.fromString(id);
        } catch (RuntimeException e) {
            throw new BusinessException("Id de evento inválido!");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado."));

        LocalDateTime now = LocalDateTime.now();

        boolean endForDate = event.getFinalDate() != null && now.isAfter(event.getFinalDate());

        if (endForDate && event.getStatus() != EventStatus.COMPLETED) {
            event.setStatus(EventStatus.COMPLETED);
            eventRepository.save(event);
        }

        if (event.getStatus() == EventStatus.COMPLETED || endForDate) {
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
                        command.hasBoardGames() == null &&
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

        if (command.street() != null) event.setStreet(command.street());
        if (command.number() != null) event.setNumber(command.number());
        if (command.supplement() != null) event.setSupplement(command.supplement());
        if (command.neighborhood() != null) event.setNeighborhood(command.neighborhood());
        if (command.city() != null) event.setCity(command.city());
        if (command.state() != null) event.setState(command.state());
        if (command.zipCode() != null) event.setZipCode(command.zipCode());

        if (event.getFinalDate().isBefore(event.getStartDate())) {
            throw new BusinessException("A data final não pode ser antes da inicial.");
        }

        boolean updatingModalidades =
                command.hasBoardGames() != null ||
                        command.hasRpg() != null ||
                        command.hasEscapeRoom() != null;

        if (updatingModalidades) {

            boolean newHasBoardGames = command.hasBoardGames() != null
                    ? command.hasBoardGames()
                    : event.getHasBoardGames();

            boolean newHasRpg = command.hasRpg() != null
                    ? command.hasRpg()
                    : event.getHasRpg();

            boolean newHasEscapeRoom = command.hasEscapeRoom() != null
                    ? command.hasEscapeRoom()
                    : event.getHasEscapeRoom();

            if (!newHasBoardGames && !newHasRpg && !newHasEscapeRoom) {
                throw new IllegalArgumentException(
                        "O evento deve possuir pelo menos uma modalidade: jogos de tabuleiro, RPG ou escape room."
                );
            }

            event.setHasBoardGames(newHasBoardGames);
            event.setHasRpg(newHasRpg);
            event.setHasEscapeRoom(newHasEscapeRoom);
        }

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
