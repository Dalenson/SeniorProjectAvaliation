package com.dale.projeto.service.validate;

import com.dale.projeto.model.QPedido;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoValidate {

    public final static QPedido qPedido = QPedido.pedido;

    @Autowired
    private JPAQueryFactory queryFactory;


}
