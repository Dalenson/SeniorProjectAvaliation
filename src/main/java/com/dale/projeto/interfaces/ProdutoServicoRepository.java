package com.dale.projeto.interfaces;

import com.dale.projeto.model.ProdutoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoServicoRepository extends JpaRepository<ProdutoServico, Long>, QuerydslPredicateExecutor<ProdutoServico> {}
