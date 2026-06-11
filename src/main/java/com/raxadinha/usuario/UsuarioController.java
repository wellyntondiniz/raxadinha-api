package com.raxadinha.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*") // Permite chamadas do Expo Snack/Mobile
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/buscar")
    public List<Usuario> buscarUsuarios(@RequestParam String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }
}
