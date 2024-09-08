package com.dale.projeto.controller;

import com.dale.projeto.model.Pedido;
import com.dale.projeto.model.ProdutoServico;
import com.dale.projeto.service.ProdutoServicoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produto-servico")
@Tag(name = "Produto/Servico", description = "Gerencia os produtos e serviços.")
public class ProdutoServicoResource {

    @Autowired
    ProdutoServicoService service;

    @GetMapping
    public Page<ProdutoServico> buscarTodos(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "25", required = false) int limit,
            @RequestParam(name = "descricao", required = false) String filterDescricao){
        return service.buscarTodos(page, limit, filterDescricao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoServico> buscarPorId(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<UUID> gravar(@RequestBody ProdutoServico produtoServico){
        return ResponseEntity.ok(service.gravar(produtoServico).getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoServico> atualizar(@PathVariable("id") UUID id, @RequestBody ProdutoServico produtoServico) {
        return ResponseEntity.ok(service.atualizar(id, produtoServico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") UUID id) {
        service.excluir(id);
        return ResponseEntity.ok().build();
    }
}
