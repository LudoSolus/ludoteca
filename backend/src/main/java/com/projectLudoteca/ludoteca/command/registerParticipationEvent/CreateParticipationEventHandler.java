package com.projectLudoteca.ludoteca.command.registerParticipationEvent;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.entity.ParticipationEvent;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.repository.EventRepository;
import com.projectLudoteca.ludoteca.common.repository.ParticipationEventRepository;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateParticipationEventHandler {

    private final ParticipationEventRepository participationEventRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public CreateParticipationEventHandler (ParticipationEventRepository participationEventRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.participationEventRepository = participationEventRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public String handle (CreateParticipationEventCommand command) {

        User user = userRepository.findByPublicIdAndRemovedFalse(command.userPublicId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Event event = eventRepository.findByIdAndRemovedFalse(command.eventId())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado."));

        if (participationEventRepository.existsByEventAndUser(event, user)) {
            throw new RuntimeException("Presença já registrada para este usuário neste evento.");
        }

        ParticipationEvent participation = new ParticipationEvent(event, user);
        participationEventRepository.save(participation);

        return "Presença registrada com sucesso!";
    }

}
