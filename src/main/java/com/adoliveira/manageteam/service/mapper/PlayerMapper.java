package com.adoliveira.manageteam.service.mapper;

import com.adoliveira.manageteam.domain.*;
import com.adoliveira.manageteam.service.dto.PlayerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Player and its DTO PlayerDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PlayerMapper extends EntityMapper<PlayerDTO, Player> {

    @Mapping(source = "player.id", target = "playerId")
    PlayerDTO toDto(Player player);

    @Mapping(source = "playerId", target = "player")
    Player toEntity(PlayerDTO playerDTO);

    default Player fromId(Long id) {
        if (id == null) {
            return null;
        }
        Player player = new Player();
        player.setId(id);
        return player;
    }
}
