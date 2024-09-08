package com.dale.projeto;

import com.dale.projeto.exception.CustomException;
import com.dale.projeto.interfaces.PedidoRepository;
import com.dale.projeto.model.Pedido;
import com.dale.projeto.model.QPedido;
import com.dale.projeto.service.PedidoService;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PedidoTest {

    @InjectMocks
    private PedidoService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private JPAQueryFactory queryFactory;

    @Mock
    private JPAQuery<Pedido> query;

    @Mock
    private PedidoRepository repository;

    @Test
    public void buscarTodos() {
        // Dados de entrada
        Integer page = 0;
        Integer limit = 10;
        String descricao = "descricao";
        String status = "ABERTO";

        // Dados esperados
        Pedido pedido = new Pedido();
        List<Pedido> pedidos = Collections.singletonList(pedido);
        Pageable pageable = PageRequest.of(page, limit);

        // Mocking
        when(queryFactory.selectFrom(QPedido.pedido)).thenReturn(query);
//        when(query.where(any())).thenReturn(query);
//        when(query.offset(pageable.getOffset())).thenReturn(query);
//        when(query.limit(pageable.getPageSize())).thenReturn(query);
//        when(query.fetch()).thenReturn(pedidos);
//
//        when(queryFactory.selectFrom(QPedido.pedido)).thenReturn(query);
//        when(query.fetch()).thenReturn(pedidos);
//
//        long total = pedidos.size();
//        when(queryFactory.selectFrom(QPedido.pedido)).thenReturn(query);
//        when(query.fetch()).thenReturn(pedidos);

        // Chamada ao método
        Page<Pedido> resultado = service.buscarTodos(page, limit, descricao, status);

//        // Verificações
//        assertEquals(pedidos, resultado.getContent());
//        assertEquals(total, resultado.getTotalElements());
//        assertEquals(pageable, resultado.getPageable());
//        verify(queryFactory, times(2)).selectFrom(QPedido.pedido);
////        verify(query, times(1)).where(any(BooleanBuilder.class));
//        verify(query, times(1)).offset(pageable.getOffset());
//        verify(query, times(1)).limit(pageable.getPageSize());
//        verify(query, times(1)).fetch();
    }

    @Test
    public void atualizar() {
        UUID id = UUID.randomUUID();
        Pedido pedido = new Pedido();
        Pedido pedidoSaved = new Pedido();

        when(queryFactory.selectFrom(any(QPedido.class))).thenReturn(query);

        when(query.where(any(Predicate.class))).thenReturn(query);

        when(query.fetchOne()).thenReturn(pedidoSaved);
        when(repository.save(any(Pedido.class))).thenReturn(pedidoSaved);


        Pedido resultado = service.atualizar(id, pedido);

        assertEquals(pedidoSaved, resultado);
        verify(queryFactory, times(1)).selectFrom(any());
        verify(query, times(1)).where(any(Predicate.class));
        verify(query, times(1)).fetchOne();
    }

    @Test
    public void atualizarThrow() {
        UUID id = UUID.randomUUID();
        Pedido pedido = new Pedido();
        Pedido pedidoSaved = new Pedido();

        when(queryFactory.selectFrom(any(QPedido.class))).thenReturn(query);

        when(query.where(any(Predicate.class))).thenReturn(query);

        when(query.fetchOne()).thenReturn(null);

        try {
            service.atualizar(id, pedido);
        } catch (CustomException e) {
            assertEquals("Pedido não foi encontrado", e.getMessage());
        }

    }
}
