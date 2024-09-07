package com.dale.projeto.service;

import com.dale.projeto.interfaces.PedidoRepository;
import com.dale.projeto.model.Pedido;
import com.dale.projeto.model.QPedido;
import com.dale.projeto.model.enums.PedidoStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository repository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    PedidoItemService pedidoItemService;

    @Autowired
    private JPAQueryFactory queryFactory;

    private static final QPedido qPedido = QPedido.pedido;

    public Page<Pedido> buscarTodos(Integer page, Integer limit, String descricao, String status){
        JPAQuery<Pedido> query = queryFactory.selectFrom(qPedido);

        BooleanBuilder builder = new BooleanBuilder();

        if(Objects.nonNull(descricao)){
            builder.and(qPedido.descricao.containsIgnoreCase(descricao));
        }

        if(Objects.nonNull(status)){
            builder.and(qPedido.status.eq(PedidoStatus.valueOf(status)));
        }

        Pageable pageable = PageRequest.of(page, limit);
        List<Pedido> pedidos = query
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.selectFrom(qPedido)
                .fetch().size();

        return new PageImpl<>(pedidos, pageable, total);
    }

    public Pedido gravar(Pedido pedido) {
        return repository.save(pedido);
    }

    public Pedido buscarPorId(UUID id) {
        return queryFactory.selectFrom(qPedido)
                .where(qPedido.id.eq(id))
                .fetchOne();
    }

    @Transactional
    public UUID atualizar(UUID id, Pedido pedido) {
        Pedido pedidoSaved = queryFactory.selectFrom(qPedido)
                .where(qPedido.id.eq(id))
                .fetchOne();

        pedidoSaved.setData(pedido.getData());
        pedidoSaved.setDescricao(pedido.getDescricao());
        pedidoSaved.setDesconto(pedido.getDesconto());
        pedidoSaved.setStatus(pedido.getStatus());
        pedidoSaved.setValorTotal(pedido.getValorTotal());

        repository.save(pedidoSaved);

        return pedidoSaved.getId();
    }

    @Transactional
    public void excluir(UUID id) {
        pedidoItemService.excluirTodosItens(id);
        queryFactory.delete(qPedido)
                .where(qPedido.id.eq(id))
                .execute();
    }
}
