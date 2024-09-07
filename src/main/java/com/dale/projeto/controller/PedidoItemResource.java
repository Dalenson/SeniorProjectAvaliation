package com.dale.projeto.controller;

import com.dale.projeto.model.Pedido;
import com.dale.projeto.model.PedidoItem;
import com.dale.projeto.service.PedidoItemService;
import com.dale.projeto.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedido-item")
public class PedidoItemResource {

    @Autowired
    PedidoItemService service;

    @GetMapping
    public ResponseEntity<List<PedidoItem>> buscar(){
        return ResponseEntity.ok(service.buscarTodos());
    }

    @PostMapping
    public ResponseEntity<UUID> gravar(@RequestBody PedidoItem pedidoItem){
        return ResponseEntity.ok(service.gravar(pedidoItem).getId());
    }
}
