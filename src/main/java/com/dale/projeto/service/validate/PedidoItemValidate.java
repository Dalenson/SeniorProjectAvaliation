package com.dale.projeto.service.validate;

import com.dale.projeto.exception.CustomException;
import com.dale.projeto.model.QPedidoItem;
import com.dale.projeto.model.QProdutoServico;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PedidoItemValidate {

    @Autowired
    private JPAQueryFactory queryFactory;

    private static final QPedidoItem qPedidoItem = QPedidoItem.pedidoItem;

    public void validateExcluir(UUID idProdutoServico) {
        boolean hasProdutoServico = !queryFactory.selectFrom(qPedidoItem)
                .where(qPedidoItem.produtoServico.id.eq(idProdutoServico))
                .limit(1)
                .fetch()
                .isEmpty();
        if(hasProdutoServico){
            throw new CustomException("Não é possivel excluir produto/servico pois ainda está vinculada a um pedido");
        }
    }
}
