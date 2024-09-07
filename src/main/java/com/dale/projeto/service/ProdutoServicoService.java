package com.dale.projeto.service;

import com.dale.projeto.interfaces.ProdutoServicoRepository;
import com.dale.projeto.model.ProdutoServico;
import com.dale.projeto.model.QProdutoServico;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServicoService {

    @Autowired
    ProdutoServicoRepository repository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private JPAQueryFactory queryFactory;

    private static final QProdutoServico qProdutoServico = QProdutoServico.produtoServico;

    public List<ProdutoServico> buscarTodos(){
        return (List<ProdutoServico>) queryFactory.selectFrom(qProdutoServico).fetch();
    }

    public ProdutoServico gravar(ProdutoServico produtoServico) {
        return repository.save(produtoServico);
    }
}
