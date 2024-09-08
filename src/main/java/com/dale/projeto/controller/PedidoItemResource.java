package com.dale.projeto.controller;

import com.dale.projeto.model.PedidoItem;
import com.dale.projeto.service.PedidoItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pedido-item")
@Tag(name = "Item Pedido", description = "Gerencia os itens de pedidos.")
public class PedidoItemResource {

    @Autowired
    PedidoItemService service;

    @GetMapping
    public Page<PedidoItem> buscar(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                   @RequestParam(name = "limit", defaultValue = "25", required = false) int limit,
                                   @RequestParam(name = "idPedido", required = false) UUID filterIdPedido) {
        return service.buscarTodos(page, limit, filterIdPedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoItem> buscarPorId(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<UUID> gravar(@RequestBody PedidoItem pedidoItem) {
        return ResponseEntity.ok(service.gravar(pedidoItem).getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoItem> atualizar(@PathVariable("id") UUID id, @RequestBody PedidoItem pedidoItem) {
        return ResponseEntity.ok(service.atualizar(id, pedidoItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") UUID id) {
        service.excluir(id);
        return ResponseEntity.ok().build();
    }
}
