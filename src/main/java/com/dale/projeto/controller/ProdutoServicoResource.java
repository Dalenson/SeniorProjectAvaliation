package com.dale.projeto.controller;

import com.dale.projeto.model.ProdutoServico;
import com.dale.projeto.service.ProdutoServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produto-servico")
public class ProdutoServicoResource {

    @Autowired
    ProdutoServicoService service;

    @GetMapping
    public ResponseEntity<List<ProdutoServico>> buscar(){
        return ResponseEntity.ok(service.buscarTodos());
    }

    @PostMapping
    public ResponseEntity<UUID> gravar(@RequestBody ProdutoServico produtoServico){
        return ResponseEntity.ok(service.gravar(produtoServico).getId());
    }
}
