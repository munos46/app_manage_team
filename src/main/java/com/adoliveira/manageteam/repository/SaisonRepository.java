package com.adoliveira.manageteam.repository;

import com.adoliveira.manageteam.domain.Saison;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Saison entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaisonRepository extends JpaRepository<Saison, Long> {

}
