package com.dale.projeto.service;

import com.dale.projeto.exception.CustomException;
import com.dale.projeto.interfaces.ProdutoServicoRepository;
import com.dale.projeto.model.ProdutoServico;
import com.dale.projeto.model.QProdutoServico;
import com.dale.projeto.service.validate.PedidoItemValidate;
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
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoServicoService {

    @Autowired
    ProdutoServicoRepository repository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    PedidoItemValidate validateServices;

    private final PedidoItemValidate pedidoItemValidate;

    public ProdutoServicoService (PedidoItemValidate pedidoItemValidate) {
        this.pedidoItemValidate = pedidoItemValidate;
    }

    private static final QProdutoServico qProdutoServico = QProdutoServico.produtoServico;

    public Page<ProdutoServico> buscarTodos(Integer page, Integer limit, String descricao){
        JPAQuery<ProdutoServico> query = queryFactory.selectFrom(qProdutoServico);

        BooleanBuilder builder = new BooleanBuilder();

        if(Objects.nonNull(descricao)){
            builder.and(qProdutoServico.descricao.containsIgnoreCase(descricao));
        }

        Pageable pageable = PageRequest.of(page, limit);
        List<ProdutoServico> produtoServicos = query
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.selectFrom(qProdutoServico)
                .fetch().size();

        return new PageImpl<>(produtoServicos, pageable, total);
    }

    public ProdutoServico gravar(ProdutoServico produtoServico) {
        return repository.save(produtoServico);
    }

    public ProdutoServico buscarPorId(UUID id) {
        return queryFactory.selectFrom(qProdutoServico)
                .where(qProdutoServico.id.eq(id))
                .fetchOne();
    }

    public ProdutoServico atualizar(UUID id, ProdutoServico produtoServico) {
        ProdutoServico produtoServicoSaved = queryFactory.selectFrom(qProdutoServico)
                .where(qProdutoServico.id.eq(id))
                .fetchOne();

        if(Objects.nonNull(produtoServicoSaved)) {
            return repository.save(merge(produtoServicoSaved, produtoServico));
        } else {
            throw new CustomException("Produto/Servico não foi encontrado");
        }
    }

    @Transactional
    public void excluir(UUID id) {
        pedidoItemValidate.validateExcluir(id);
        queryFactory.delete(qProdutoServico)
                .where(qProdutoServico.id.eq(id))
                .execute();
    }

    private ProdutoServico merge(ProdutoServico saved, ProdutoServico update) {
        Optional.ofNullable(update.getDescricao()).ifPresent(saved::setDescricao);
        Optional.ofNullable(update.isAtivo()).ifPresent(saved::setAtivo);
        Optional.ofNullable(update.getTipo()).ifPresent(saved::setTipo);

        return saved;
    }
}
