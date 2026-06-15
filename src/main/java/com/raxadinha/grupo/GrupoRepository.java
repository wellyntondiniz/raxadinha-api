package com.raxadinha.grupo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE eventos SET grupo_id = NULL WHERE grupo_id = :grupoId", nativeQuery = true)
    void desvincularEventosDoGrupo(Integer grupoId);
}
