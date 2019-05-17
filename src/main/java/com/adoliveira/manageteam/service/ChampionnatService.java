package com.adoliveira.manageteam.service;

import com.adoliveira.manageteam.service.dto.ChampionnatDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Championnat.
 */
public interface ChampionnatService {

    /**
     * Save a championnat.
     *
     * @param championnatDTO the entity to save
     * @return the persisted entity
     */
    ChampionnatDTO save(ChampionnatDTO championnatDTO);

    /**
     * Get all the championnats.
     *
     * @return the list of entities
     */
    List<ChampionnatDTO> findAll();

    /**
     * Get all the Championnat with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ChampionnatDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" championnat.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ChampionnatDTO> findOne(Long id);

    /**
     * Delete the "id" championnat.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
