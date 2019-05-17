package com.adoliveira.manageteam.repository;

import com.adoliveira.manageteam.domain.Practise;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Practise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PractiseRepository extends JpaRepository<Practise, Long> {

    @Query(value = "select distinct practise from Practise practise left join fetch practise.players left join fetch practise.manages",
        countQuery = "select count(distinct practise) from Practise practise")
    Page<Practise> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct practise from Practise practise left join fetch practise.players left join fetch practise.manages")
    List<Practise> findAllWithEagerRelationships();

    @Query("select practise from Practise practise left join fetch practise.players left join fetch practise.manages where practise.id =:id")
    Optional<Practise> findOneWithEagerRelationships(@Param("id") Long id);

}
