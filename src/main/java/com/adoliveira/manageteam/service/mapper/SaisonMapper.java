package com.adoliveira.manageteam.service.mapper;

import com.adoliveira.manageteam.domain.*;
import com.adoliveira.manageteam.service.dto.SaisonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Saison and its DTO SaisonDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SaisonMapper extends EntityMapper<SaisonDTO, Saison> {



    default Saison fromId(Long id) {
        if (id == null) {
            return null;
        }
        Saison saison = new Saison();
        saison.setId(id);
        return saison;
    }
}
