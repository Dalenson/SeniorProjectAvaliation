package com.dale.projeto.service;

import com.dale.projeto.exception.CustomException;
import com.dale.projeto.interfaces.PedidoItemRepository;
import com.dale.projeto.model.PedidoItem;
import com.dale.projeto.model.QPedidoItem;
import com.dale.projeto.model.enums.TipoEnum;
import com.dale.projeto.service.validate.ProdutoServicoValidate;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoItemService {

    @Autowired
    PedidoItemRepository repository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private JPAQueryFactory queryFactory;

    public final ProdutoServicoValidate produtoServicoValidate;

    public PedidoItemService (PedidoItemRepository repository, JPAQueryFactory queryFactory, ProdutoServicoValidate produtoServicoValidate) {
        this.repository = repository;
        this.queryFactory = queryFactory;
        this.produtoServicoValidate = produtoServicoValidate;
    }

    private static final QPedidoItem qPedidoItem = QPedidoItem.pedidoItem;

    public Page<PedidoItem> buscarTodos(Integer page, Integer limit, UUID filterIdPedido){
        JPAQuery<PedidoItem> query = queryFactory.selectFrom(qPedidoItem);

        BooleanBuilder builder = new BooleanBuilder();

        if(Objects.nonNull(filterIdPedido)){
            builder.and(qPedidoItem.pedido.id.eq(filterIdPedido));
        }

        Pageable pageable = PageRequest.of(page, limit);
        List<PedidoItem> pedidoItems = query
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.selectFrom(qPedidoItem)
                .fetch().size();

        return new PageImpl<>(pedidoItems, pageable, total);
    }

    public PedidoItem gravar(PedidoItem pedidoItem) {
        produtoServicoValidate.isDesativado(pedidoItem.getProdutoServico().getId());
        return repository.save(pedidoItem);
    }

    public void excluirTodosItens(UUID idPedido) {
        queryFactory.delete(qPedidoItem)
                .where(qPedidoItem.pedido.id.eq(idPedido))
                .execute();
    }

    @Transactional
    public PedidoItem atualizar(UUID id, PedidoItem pedidoItem) {
        PedidoItem pedidoItemSaved = queryFactory.selectFrom(qPedidoItem)
                .where(qPedidoItem.id.eq(id))
                .fetchOne();

        if(Objects.nonNull(pedidoItemSaved)) {
            return repository.save(merge(pedidoItemSaved, pedidoItem));
        } else {
            throw new CustomException("Item do pedido não foi encontrado");
        }
    }

    @Transactional
    public void excluir(UUID id) {
        queryFactory.delete(qPedidoItem)
                .where(qPedidoItem.id.eq(id))
                .execute();
    }

    public PedidoItem buscarPorId(UUID id) {
        return queryFactory.selectFrom(qPedidoItem)
                .where(qPedidoItem.id.eq(id))
                .fetchOne();
    }

    private PedidoItem merge(PedidoItem saved, PedidoItem update) {
        Optional.ofNullable(update.getValorTotal()).ifPresent(saved::setValorTotal);
        Optional.ofNullable(update.getQuantidade()).ifPresent(saved::setQuantidade);
        Optional.ofNullable(update.getPrecoUnitario()).ifPresent(saved::setPrecoUnitario);
        Optional.ofNullable(update.getProdutoServico()).ifPresent(saved::setProdutoServico);

        return saved;
    }

    public void aplicarDesconto(UUID idPedido, BigDecimal desconto) {
        List<PedidoItem> listaPedidoItens = queryFactory.selectFrom(qPedidoItem)
                .where(qPedidoItem.pedido.id.eq(idPedido))
                .fetch();

        listaPedidoItens.forEach(itens -> {
            if(TipoEnum.PRODUTO.equals(itens.getProdutoServico().getTipo())) {
                BigDecimal descontoItem = itens.getValorTotal().multiply(desconto);
                BigDecimal valorTotalItem = itens.getValorTotal().subtract(descontoItem);

                queryFactory.update(qPedidoItem)
                        .set(qPedidoItem.valorTotal, valorTotalItem)
                        .where(qPedidoItem.id.eq(itens.getId()))
                        .execute();
            }
        });
    }
}
