package com.adoliveira.manageteam.service.mapper;

import com.adoliveira.manageteam.domain.*;
import com.adoliveira.manageteam.service.dto.ChampionnatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Championnat and its DTO ChampionnatDTO.
 */
@Mapper(componentModel = "spring", uses = {SaisonMapper.class, TeamMapper.class, GameMapper.class})
public interface ChampionnatMapper extends EntityMapper<ChampionnatDTO, Championnat> {

    @Mapping(source = "saison.id", target = "saisonId")
    ChampionnatDTO toDto(Championnat championnat);

    @Mapping(source = "saisonId", target = "saison")
    Championnat toEntity(ChampionnatDTO championnatDTO);

    default Championnat fromId(Long id) {
        if (id == null) {
            return null;
        }
        Championnat championnat = new Championnat();
        championnat.setId(id);
        return championnat;
    }
}
