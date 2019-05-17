package com.adoliveira.manageteam.repository;

import com.adoliveira.manageteam.domain.Championnat;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Championnat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChampionnatRepository extends JpaRepository<Championnat, Long> {

    @Query(value = "select distinct championnat from Championnat championnat left join fetch championnat.adversaires left join fetch championnat.journees",
        countQuery = "select count(distinct championnat) from Championnat championnat")
    Page<Championnat> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct championnat from Championnat championnat left join fetch championnat.adversaires left join fetch championnat.journees")
    List<Championnat> findAllWithEagerRelationships();

    @Query("select championnat from Championnat championnat left join fetch championnat.adversaires left join fetch championnat.journees where championnat.id =:id")
    Optional<Championnat> findOneWithEagerRelationships(@Param("id") Long id);

}
