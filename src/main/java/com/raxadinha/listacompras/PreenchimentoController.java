package com.raxadinha.listacompras;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preenchimento")
@CrossOrigin(origins = "*")
public class PreenchimentoController {

    private final PreenchimentoService service;

    public PreenchimentoController(PreenchimentoService service) {
        this.service = service;
    }

    @GetMapping("/lista/{listaId}")
    public List<PreenchimentoItem> listarPorLista(@PathVariable Long listaId) {
        return service.listarPorLista(listaId);
    }

    @PostMapping("/lote")
    public List<PreenchimentoItem> salvarLote(@RequestBody List<PreenchimentoItem> preenchimentos) {
        return service.salvarLote(preenchimentos);
    }
}
