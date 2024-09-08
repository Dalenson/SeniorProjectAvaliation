package com.dale.projeto.controller;

import com.dale.projeto.model.Pedido;
import com.dale.projeto.model.dto.DescontoDTO;
import com.dale.projeto.service.PedidoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pedido")
@Tag(name = "Pedido", description = "Gerencia os pedidos.")
public class PedidoResource {

    @Autowired
    PedidoService service;

    @GetMapping
    public Page<Pedido> buscarTodos(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "25", required = false) int limit,
            @RequestParam(name = "descricao", required = false) String filterDescricao,
            @RequestParam(name = "status", required = false) String filterStatus) {
        return service.buscarTodos(page, limit, filterDescricao, filterStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<UUID> gravar(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(service.gravar(pedido).getId());
    }

    @PostMapping("/aplicar-desconto")
    public ResponseEntity aplicarDesconto(@RequestBody DescontoDTO descontoDTO) {
        service.aplicarDesconto(descontoDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable("id") UUID id, @RequestBody Pedido pedido) {
        return ResponseEntity.ok(service.atualizar(id, pedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") UUID id) {
        service.excluir(id);
        return ResponseEntity.ok().build();
    }
}
