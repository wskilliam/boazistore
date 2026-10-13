package com.baozi.baozistore.model;

/** Corpo JSON recebido para criar/atualizar um pedido. */
public record PedidoRequest(Long clienteId, Long produtoId, Integer quantidade) {}
