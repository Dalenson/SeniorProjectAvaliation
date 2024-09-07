package com.dale.projeto.interfaces;

import com.dale.projeto.model.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long>, QuerydslPredicateExecutor<PedidoItem> {}
