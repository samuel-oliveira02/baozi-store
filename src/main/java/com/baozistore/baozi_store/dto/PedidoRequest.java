package com.baozistore.baozi_store.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PedidoRequest {

    @NotNull(message = "Informe o ID do cliente")
    private Long clienteId;

    @NotNull(message = "Informe o ID do produto")
    private Long produtoId;

    @NotNull(message = "Informe a quantidade")
    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    private Integer quantidade;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}