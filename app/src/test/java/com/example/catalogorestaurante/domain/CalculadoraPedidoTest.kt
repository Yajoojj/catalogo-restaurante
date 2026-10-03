package com.example.catalogorestaurante.domain

import com.example.catalogorestaurante.model.Bebida
import com.example.catalogorestaurante.model.FormaPagamento
import com.example.catalogorestaurante.model.Prato
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculadoraPedidoTest {

    private val pedido = listOf(
        Prato("Pizza Margherita", 42.00, vegetariano = true),
        Prato("Feijoada completa", 58.00, vegetariano = false),
        Bebida("Suco de laranja", 12.00, alcoolica = false)
    )
    private val pix = FormaPagamento.Pix(10.0)

    @Test
    fun cenarioDoEnunciadoNoPix() {
        assertEquals(112.00, CalculadoraPedido.subtotal(pedido), 0.001)
        assertEquals(11.20, CalculadoraPedido.taxaServico(pedido, pix), 0.001)
        assertEquals(11.20, CalculadoraPedido.desconto(pedido, pix), 0.001)
        assertEquals(112.00, CalculadoraPedido.total(pedido, pix), 0.001)
    }

    @Test
    fun dinheiroECartaoNaoTemDesconto() {
        assertEquals(0.0, CalculadoraPedido.desconto(pedido, FormaPagamento.Dinheiro), 0.001)
        assertEquals(123.20, CalculadoraPedido.total(pedido, FormaPagamento.Dinheiro), 0.001)
        assertEquals(123.20, CalculadoraPedido.total(pedido, FormaPagamento.Cartao), 0.001)
    }

    @Test
    fun agrupaPratosEBebidas() {
        val grupos = CalculadoraPedido.agruparPorCategoria(pedido)
        assertEquals(2, grupos["Pratos"]?.size)
        assertEquals(1, grupos["Bebidas"]?.size)
    }

    @Test
    fun carrinhoVazio() {
        assertEquals(0.0, CalculadoraPedido.total(emptyList(), pix), 0.001)
    }
}
