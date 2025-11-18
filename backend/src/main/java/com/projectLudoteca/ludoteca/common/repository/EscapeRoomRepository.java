package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.EscapeRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EscapeRoomRepository extends CrudRepository<EscapeRoom, UUID> {
}
