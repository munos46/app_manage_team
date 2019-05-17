package com.adoliveira.manageteam.service.mapper;

import com.adoliveira.manageteam.domain.*;
import com.adoliveira.manageteam.service.dto.ManagerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Manager and its DTO ManagerDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ManagerMapper extends EntityMapper<ManagerDTO, Manager> {

    @Mapping(source = "manager.id", target = "managerId")
    ManagerDTO toDto(Manager manager);

    @Mapping(source = "managerId", target = "manager")
    Manager toEntity(ManagerDTO managerDTO);

    default Manager fromId(Long id) {
        if (id == null) {
            return null;
        }
        Manager manager = new Manager();
        manager.setId(id);
        return manager;
    }
}
