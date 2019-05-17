package com.adoliveira.manageteam.service.mapper;

import com.adoliveira.manageteam.domain.*;
import com.adoliveira.manageteam.service.dto.StadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Stade and its DTO StadeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StadeMapper extends EntityMapper<StadeDTO, Stade> {



    default Stade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Stade stade = new Stade();
        stade.setId(id);
        return stade;
    }
}
