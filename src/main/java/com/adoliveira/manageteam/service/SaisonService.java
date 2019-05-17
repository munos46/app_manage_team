package com.adoliveira.manageteam.service;

import com.adoliveira.manageteam.service.dto.SaisonDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Saison.
 */
public interface SaisonService {

    /**
     * Save a saison.
     *
     * @param saisonDTO the entity to save
     * @return the persisted entity
     */
    SaisonDTO save(SaisonDTO saisonDTO);

    /**
     * Get all the saisons.
     *
     * @return the list of entities
     */
    List<SaisonDTO> findAll();


    /**
     * Get the "id" saison.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SaisonDTO> findOne(Long id);

    /**
     * Delete the "id" saison.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
