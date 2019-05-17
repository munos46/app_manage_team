package com.adoliveira.manageteam.service.impl;

import com.adoliveira.manageteam.service.PractiseService;
import com.adoliveira.manageteam.domain.Practise;
import com.adoliveira.manageteam.repository.PractiseRepository;
import com.adoliveira.manageteam.service.dto.PractiseDTO;
import com.adoliveira.manageteam.service.mapper.PractiseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Practise.
 */
@Service
@Transactional
public class PractiseServiceImpl implements PractiseService {

    private final Logger log = LoggerFactory.getLogger(PractiseServiceImpl.class);

    private final PractiseRepository practiseRepository;

    private final PractiseMapper practiseMapper;

    public PractiseServiceImpl(PractiseRepository practiseRepository, PractiseMapper practiseMapper) {
        this.practiseRepository = practiseRepository;
        this.practiseMapper = practiseMapper;
    }

    /**
     * Save a practise.
     *
     * @param practiseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PractiseDTO save(PractiseDTO practiseDTO) {
        log.debug("Request to save Practise : {}", practiseDTO);
        Practise practise = practiseMapper.toEntity(practiseDTO);
        practise = practiseRepository.save(practise);
        return practiseMapper.toDto(practise);
    }

    /**
     * Get all the practises.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PractiseDTO> findAll() {
        log.debug("Request to get all Practises");
        return practiseRepository.findAllWithEagerRelationships().stream()
            .map(practiseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Practise with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<PractiseDTO> findAllWithEagerRelationships(Pageable pageable) {
        return practiseRepository.findAllWithEagerRelationships(pageable).map(practiseMapper::toDto);
    }
    

    /**
     * Get one practise by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PractiseDTO> findOne(Long id) {
        log.debug("Request to get Practise : {}", id);
        return practiseRepository.findOneWithEagerRelationships(id)
            .map(practiseMapper::toDto);
    }

    /**
     * Delete the practise by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Practise : {}", id);
        practiseRepository.deleteById(id);
    }
}
