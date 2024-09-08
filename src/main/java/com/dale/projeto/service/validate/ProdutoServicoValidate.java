package com.dale.projeto.service.validate;

import com.dale.projeto.model.QProdutoServico;
import com.dale.projeto.model.enums.TipoEnum;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProdutoServicoValidate {

    @Autowired
    private JPAQueryFactory queryFactory;

    private static final QProdutoServico qProdutoServico = QProdutoServico.produtoServico;

    public boolean isDesativado(UUID idProdutoServico) {
        return queryFactory.from(qProdutoServico)
                .where(qProdutoServico.id.eq(idProdutoServico)
                        .and(qProdutoServico.ativo.eq(false)))
                .fetch()
                .isEmpty();
    }
}
