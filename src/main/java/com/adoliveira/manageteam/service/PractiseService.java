package com.adoliveira.manageteam.service;

import com.adoliveira.manageteam.service.dto.PractiseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Practise.
 */
public interface PractiseService {

    /**
     * Save a practise.
     *
     * @param practiseDTO the entity to save
     * @return the persisted entity
     */
    PractiseDTO save(PractiseDTO practiseDTO);

    /**
     * Get all the practises.
     *
     * @return the list of entities
     */
    List<PractiseDTO> findAll();

    /**
     * Get all the Practise with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<PractiseDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" practise.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PractiseDTO> findOne(Long id);

    /**
     * Delete the "id" practise.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
