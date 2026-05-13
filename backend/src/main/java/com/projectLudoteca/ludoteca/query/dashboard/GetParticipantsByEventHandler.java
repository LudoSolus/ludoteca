package com.projectLudoteca.ludoteca.query.dashboard;

import com.projectLudoteca.ludoteca.common.repository.ParticipationEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetParticipantsByEventHandler {

    private final ParticipationEventRepository participationEventRepository;

    public GetParticipantsByEventHandler(ParticipationEventRepository participationEventRepository) {
        this.participationEventRepository = participationEventRepository;
    }

    public ParticipantsByEventResponse handle() {
        List<ParticipantsByEventData> data = participationEventRepository.countParticipantsByEvent();
        return new ParticipantsByEventResponse(data);
    }
}
