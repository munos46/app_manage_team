package com.adoliveira.manageteam.service.mapper;

import com.adoliveira.manageteam.domain.*;
import com.adoliveira.manageteam.service.dto.ActionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Action and its DTO ActionDTO.
 */
@Mapper(componentModel = "spring", uses = {PlayerMapper.class})
public interface ActionMapper extends EntityMapper<ActionDTO, Action> {

    @Mapping(source = "playerOne.id", target = "playerOneId")
    @Mapping(source = "playerTwo.id", target = "playerTwoId")
    ActionDTO toDto(Action action);

    @Mapping(source = "playerOneId", target = "playerOne")
    @Mapping(source = "playerTwoId", target = "playerTwo")
    Action toEntity(ActionDTO actionDTO);

    default Action fromId(Long id) {
        if (id == null) {
            return null;
        }
        Action action = new Action();
        action.setId(id);
        return action;
    }
}
