package com.dale.projeto;

import com.dale.projeto.exception.CustomException;
import com.dale.projeto.interfaces.PedidoRepository;
import com.dale.projeto.model.Pedido;
import com.dale.projeto.model.QPedido;
import com.dale.projeto.model.dto.DescontoDTO;
import com.dale.projeto.model.enums.PedidoStatus;
import com.dale.projeto.service.PedidoItemService;
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

import java.math.BigDecimal;
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

    @Mock
    private PedidoItemService pedidoItemService;

    private static final QPedido qPedido = QPedido.pedido;

    @Test
    public void buscarTodos() {
        int page = 0;
        int limit = 10;
        String descricao = "descricao";
        String status = "ABERTO";

        Pedido pedido = new Pedido();
        List<Pedido> pedidos = Collections.singletonList(pedido);
        Pageable pageable = PageRequest.of(page, limit);

        when(queryFactory.selectFrom(qPedido)).thenReturn(query);
        when(query.where(any(Predicate.class))).thenReturn(query);
        when(query.offset(pageable.getOffset())).thenReturn(query);
        when(query.limit(pageable.getPageSize())).thenReturn(query);
        when(query.fetch()).thenReturn(pedidos);

        when(queryFactory.selectFrom(qPedido)).thenReturn(query);
        when(query.fetch()).thenReturn(pedidos);

        long total = pedidos.size();
        when(queryFactory.selectFrom(qPedido)).thenReturn(query);
        when(query.fetch()).thenReturn(pedidos);

        Page<Pedido> resultado = service.buscarTodos(page, limit, descricao, status);

        assertEquals(pedidos, resultado.getContent());
        assertEquals(total, resultado.getTotalElements());
        assertEquals(pageable, resultado.getPageable());
        verify(queryFactory, times(2)).selectFrom(qPedido);
        verify(query, times(1)).where(any(Predicate.class));
        verify(query, times(1)).offset(pageable.getOffset());
        verify(query, times(1)).limit(pageable.getPageSize());
        verify(query, times(2)).fetch();
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

        when(queryFactory.selectFrom(any(QPedido.class))).thenReturn(query);

        when(query.where(any(Predicate.class))).thenReturn(query);

        when(query.fetchOne()).thenReturn(null);

        try {
            service.atualizar(id, pedido);
        } catch (CustomException e) {
            assertEquals("Pedido não foi encontrado", e.getMessage());
        }

    }

    @Test
    public void aplicarDesconto() {
        DescontoDTO dto = new DescontoDTO();
        dto.setPorcentagem(10L);
        dto.setIdPedido(UUID.randomUUID());

        Pedido pedidoSaved = new Pedido();

        pedidoSaved.setStatus(PedidoStatus.ABERTO);

        when(queryFactory.selectFrom(any(QPedido.class))).thenReturn(query);
        when(query.where(any(Predicate.class))).thenReturn(query);
        when(query.fetchOne()).thenReturn(pedidoSaved);

        service.aplicarDesconto(dto);

        BigDecimal descontoEsperado = BigDecimal.valueOf(0.1);
        verify(pedidoItemService, times(1)).aplicarDesconto(dto.getIdPedido(), descontoEsperado);
        verify(queryFactory, times(1)).selectFrom(qPedido);
    }

    @Test
    public void aplicarDesconto_pedidoNaoEncontrado() {
        DescontoDTO dto = new DescontoDTO();
        dto.setPorcentagem(10L);
        dto.setIdPedido(UUID.randomUUID());

        Pedido pedidoSaved = new Pedido();

        pedidoSaved.setStatus(PedidoStatus.FECHADO);

        when(queryFactory.selectFrom(any(QPedido.class))).thenReturn(query);
        when(query.where(any(Predicate.class))).thenReturn(query);
        when(query.fetchOne()).thenReturn(pedidoSaved);

        service.aplicarDesconto(dto);
        verify(pedidoItemService, never()).aplicarDesconto(any(UUID.class), any(BigDecimal.class));

    }

}
