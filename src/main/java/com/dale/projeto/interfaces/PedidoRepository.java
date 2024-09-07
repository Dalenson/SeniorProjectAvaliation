package com.dale.projeto.interfaces;

import com.dale.projeto.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>, QuerydslPredicateExecutor<Pedido> {}
