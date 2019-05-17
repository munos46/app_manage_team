package com.adoliveira.manageteam.service;

import com.adoliveira.manageteam.service.dto.StadeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Stade.
 */
public interface StadeService {

    /**
     * Save a stade.
     *
     * @param stadeDTO the entity to save
     * @return the persisted entity
     */
    StadeDTO save(StadeDTO stadeDTO);

    /**
     * Get all the stades.
     *
     * @return the list of entities
     */
    List<StadeDTO> findAll();


    /**
     * Get the "id" stade.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StadeDTO> findOne(Long id);

    /**
     * Delete the "id" stade.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
