package com.dale.projeto.service;

import com.dale.projeto.interfaces.PedidoItemRepository;
import com.dale.projeto.interfaces.PedidoRepository;
import com.dale.projeto.model.Pedido;
import com.dale.projeto.model.PedidoItem;
import com.dale.projeto.model.QPedido;
import com.dale.projeto.model.QPedidoItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PedidoItemService {

    @Autowired
    PedidoItemRepository repository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private JPAQueryFactory queryFactory;

    private static final QPedidoItem qPedidoItem = QPedidoItem.pedidoItem;

    public List<PedidoItem> buscarTodos(){
        return (List<PedidoItem>) queryFactory.selectFrom(qPedidoItem).fetch();
    }

    public PedidoItem gravar(PedidoItem pedidoItem) {
        return repository.save(pedidoItem);
    }

    public void excluirTodosItens(UUID idPedido) {
        queryFactory.delete(qPedidoItem)
                .where(qPedidoItem.pedido.id.eq(idPedido))
                .execute();
    }
}
