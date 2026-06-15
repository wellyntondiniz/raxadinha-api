package com.raxadinha.grupo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByGrupoIdOrderByDataEnvioAsc(Long grupoId);
}
