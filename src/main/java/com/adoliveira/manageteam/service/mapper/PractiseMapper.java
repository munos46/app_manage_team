package com.adoliveira.manageteam.service.mapper;

import com.adoliveira.manageteam.domain.*;
import com.adoliveira.manageteam.service.dto.PractiseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Practise and its DTO PractiseDTO.
 */
@Mapper(componentModel = "spring", uses = {StadeMapper.class, PlayerMapper.class, ManagerMapper.class})
public interface PractiseMapper extends EntityMapper<PractiseDTO, Practise> {

    @Mapping(source = "stade.id", target = "stadeId")
    PractiseDTO toDto(Practise practise);

    @Mapping(source = "stadeId", target = "stade")
    Practise toEntity(PractiseDTO practiseDTO);

    default Practise fromId(Long id) {
        if (id == null) {
            return null;
        }
        Practise practise = new Practise();
        practise.setId(id);
        return practise;
    }
}
