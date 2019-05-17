package com.adoliveira.manageteam.service.mapper;

import com.adoliveira.manageteam.domain.*;
import com.adoliveira.manageteam.service.dto.GameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Game and its DTO GameDTO.
 */
@Mapper(componentModel = "spring", uses = {TeamMapper.class, StadeMapper.class, ManagerMapper.class, PlayerMapper.class, ActionMapper.class})
public interface GameMapper extends EntityMapper<GameDTO, Game> {

    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "secondTeam.id", target = "secondTeamId")
    @Mapping(source = "stade.id", target = "stadeId")
    GameDTO toDto(Game game);

    @Mapping(source = "teamId", target = "team")
    @Mapping(source = "secondTeamId", target = "secondTeam")
    @Mapping(source = "stadeId", target = "stade")
    Game toEntity(GameDTO gameDTO);

    default Game fromId(Long id) {
        if (id == null) {
            return null;
        }
        Game game = new Game();
        game.setId(id);
        return game;
    }
}
