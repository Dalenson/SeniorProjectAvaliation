package com.dale.projeto;

import com.dale.projeto.exception.CustomException;
import com.dale.projeto.interfaces.PedidoItemRepository;
import com.dale.projeto.model.PedidoItem;
import com.dale.projeto.model.QPedidoItem;
import com.dale.projeto.service.PedidoItemService;
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

public class PedidoItemTest {

    @InjectMocks
    private PedidoItemService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private JPAQueryFactory queryFactory;

    @Mock
    private JPAQuery<PedidoItem> query;

    @Mock
    private PedidoItemRepository repository;

    private static final QPedidoItem qPedidoItem = QPedidoItem.pedidoItem;

    @Test
    public void buscarTodos() {
        int page = 0;
        int limit = 10;
        UUID uuid = UUID.randomUUID();

        PedidoItem pedidoItem = new PedidoItem();
        List<PedidoItem> pedidosItens = Collections.singletonList(pedidoItem);
        Pageable pageable = PageRequest.of(page, limit);

        when(queryFactory.selectFrom(qPedidoItem)).thenReturn(query);
        when(query.where(any(Predicate.class))).thenReturn(query);
        when(query.offset(pageable.getOffset())).thenReturn(query);
        when(query.limit(pageable.getPageSize())).thenReturn(query);
        when(query.fetch()).thenReturn(pedidosItens);

        when(queryFactory.selectFrom(qPedidoItem)).thenReturn(query);
        when(query.fetch()).thenReturn(pedidosItens);

        long total = pedidosItens.size();
        when(queryFactory.selectFrom(qPedidoItem)).thenReturn(query);
        when(query.fetch()).thenReturn(pedidosItens);

        Page<PedidoItem> resultado = service.buscarTodos(page, limit, uuid);

        assertEquals(pedidosItens, resultado.getContent());
        assertEquals(total, resultado.getTotalElements());
        assertEquals(pageable, resultado.getPageable());
        verify(queryFactory, times(2)).selectFrom(qPedidoItem);
        verify(query, times(1)).where(any(Predicate.class));
        verify(query, times(1)).offset(pageable.getOffset());
        verify(query, times(1)).limit(pageable.getPageSize());
        verify(query, times(2)).fetch();
    }

    @Test
    public void atualizar() {
        int page = 0;
        int limit = 10;
        UUID uuid = UUID.randomUUID();
        PedidoItem pedidoItem = new PedidoItem();
        Pageable pageable = PageRequest.of(page, limit);
        when(queryFactory.selectFrom(qPedidoItem)).thenReturn(query);
        when(query.where(any(Predicate.class))).thenReturn(query);
        when(query.offset(pageable.getOffset())).thenReturn(query);
        when(query.limit(pageable.getPageSize())).thenReturn(query);
        when(query.fetchOne()).thenReturn(pedidoItem);
        service.atualizar(uuid, pedidoItem);
        verify(queryFactory, times(1)).selectFrom(qPedidoItem);
        verify(query, times(1)).where(any(Predicate.class));
        verify(query, times(1)).fetchOne();
    }

    @Test
    public void atualizarTrhow() {
        int page = 0;
        int limit = 10;
        UUID uuid = UUID.randomUUID();

        PedidoItem pedidoItem = new PedidoItem();
        Pageable pageable = PageRequest.of(page, limit);
        when(queryFactory.selectFrom(qPedidoItem)).thenReturn(query);
        when(query.where(any(Predicate.class))).thenReturn(query);
        when(query.offset(pageable.getOffset())).thenReturn(query);
        when(query.limit(pageable.getPageSize())).thenReturn(query);
        try {
            service.atualizar(uuid, pedidoItem);
        } catch (CustomException e) {
            assertEquals("Item do pedido não foi encontrado", e.getMessage());
        }
    }
}
