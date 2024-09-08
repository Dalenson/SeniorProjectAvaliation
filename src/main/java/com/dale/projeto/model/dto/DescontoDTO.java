package com.dale.projeto.model.dto;

import java.util.UUID;

public class DescontoDTO {

    Long porcentagem;

    UUID idPedido;

    public Long getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(Long porcentagem) {
        this.porcentagem = porcentagem;
    }

    public UUID getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(UUID idPedido) {
        this.idPedido = idPedido;
    }
}
