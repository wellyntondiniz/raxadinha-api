package com.raxadinha.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
    
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
    java.util.Optional<Usuario> findFirstByNome(String nome);
}
