package com.adoliveira.manageteam.service;

import com.adoliveira.manageteam.service.dto.ManagerDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Manager.
 */
public interface ManagerService {

    /**
     * Save a manager.
     *
     * @param managerDTO the entity to save
     * @return the persisted entity
     */
    ManagerDTO save(ManagerDTO managerDTO);

    /**
     * Get all the managers.
     *
     * @return the list of entities
     */
    List<ManagerDTO> findAll();


    /**
     * Get the "id" manager.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ManagerDTO> findOne(Long id);

    /**
     * Delete the "id" manager.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
