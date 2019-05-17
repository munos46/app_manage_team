package com.adoliveira.manageteam.service.impl;

import com.adoliveira.manageteam.service.StadeService;
import com.adoliveira.manageteam.domain.Stade;
import com.adoliveira.manageteam.repository.StadeRepository;
import com.adoliveira.manageteam.service.dto.StadeDTO;
import com.adoliveira.manageteam.service.mapper.StadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Stade.
 */
@Service
@Transactional
public class StadeServiceImpl implements StadeService {

    private final Logger log = LoggerFactory.getLogger(StadeServiceImpl.class);

    private final StadeRepository stadeRepository;

    private final StadeMapper stadeMapper;

    public StadeServiceImpl(StadeRepository stadeRepository, StadeMapper stadeMapper) {
        this.stadeRepository = stadeRepository;
        this.stadeMapper = stadeMapper;
    }

    /**
     * Save a stade.
     *
     * @param stadeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StadeDTO save(StadeDTO stadeDTO) {
        log.debug("Request to save Stade : {}", stadeDTO);
        Stade stade = stadeMapper.toEntity(stadeDTO);
        stade = stadeRepository.save(stade);
        return stadeMapper.toDto(stade);
    }

    /**
     * Get all the stades.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StadeDTO> findAll() {
        log.debug("Request to get all Stades");
        return stadeRepository.findAll().stream()
            .map(stadeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stade by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StadeDTO> findOne(Long id) {
        log.debug("Request to get Stade : {}", id);
        return stadeRepository.findById(id)
            .map(stadeMapper::toDto);
    }

    /**
     * Delete the stade by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stade : {}", id);
        stadeRepository.deleteById(id);
    }
}
