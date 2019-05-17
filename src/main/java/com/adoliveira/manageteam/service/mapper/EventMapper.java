package com.adoliveira.manageteam.service.mapper;

import com.adoliveira.manageteam.domain.*;
import com.adoliveira.manageteam.service.dto.EventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Event and its DTO EventDTO.
 */
@Mapper(componentModel = "spring", uses = {TeamMapper.class, StadeMapper.class})
public interface EventMapper extends EntityMapper<EventDTO, Event> {

    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "stade.id", target = "stadeId")
    EventDTO toDto(Event event);

    @Mapping(source = "teamId", target = "team")
    @Mapping(source = "stadeId", target = "stade")
    Event toEntity(EventDTO eventDTO);

    default Event fromId(Long id) {
        if (id == null) {
            return null;
        }
        Event event = new Event();
        event.setId(id);
        return event;
    }
}
