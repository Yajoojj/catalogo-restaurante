package com.example.catalogorestaurante.domain

import com.example.catalogorestaurante.model.Bebida
import com.example.catalogorestaurante.model.FormaPagamento
import com.example.catalogorestaurante.model.ItemMenu
import com.example.catalogorestaurante.model.Prato
import java.util.Locale

object CalculadoraPedido {

    private const val TAXA_SERVICO = 0.10

    fun subtotal(itens: List<ItemMenu>): Double = itens.sumOf { it.preco }

    fun taxaServico(itens: List<ItemMenu>, forma: FormaPagamento): Double {
        val percentual = when (forma) {
            FormaPagamento.Dinheiro -> TAXA_SERVICO
            FormaPagamento.Cartao -> TAXA_SERVICO
            is FormaPagamento.Pix -> TAXA_SERVICO
        }
        return subtotal(itens) * percentual
    }

    fun desconto(itens: List<ItemMenu>, forma: FormaPagamento): Double = when (forma) {
        FormaPagamento.Dinheiro -> 0.0
        FormaPagamento.Cartao -> 0.0
        is FormaPagamento.Pix -> subtotal(itens) * forma.percentualDesconto / 100
    }

    fun total(itens: List<ItemMenu>, forma: FormaPagamento): Double =
        subtotal(itens) + taxaServico(itens, forma) - desconto(itens, forma)

    fun agruparPorCategoria(itens: List<ItemMenu>): Map<String, List<ItemMenu>> =
        itens.groupBy {
            when (it) {
                is Prato -> "Pratos"
                is Bebida -> "Bebidas"
            }
        }

    fun gerarRelatorio(itens: List<ItemMenu>, forma: FormaPagamento): String = buildString {
        appendLine("===== PEDIDO =====")
        agruparPorCategoria(itens).forEach { (categoria, lista) ->
            appendLine("$categoria (${lista.size})")
            lista.forEach { appendLine("  ${it.nome} - ${formatarReal(it.preco)}") }
        }
        appendLine("Pagamento: ${nomeDe(forma)}")
        appendLine("Subtotal: ${formatarReal(subtotal(itens))}")
        appendLine("Taxa de serviço: ${formatarReal(taxaServico(itens, forma))}")
        appendLine("Desconto: ${formatarReal(desconto(itens, forma))}")
        append("Total: ${formatarReal(total(itens, forma))}")
    }

    fun formatarReal(valor: Double): String =
        String.format(Locale("pt", "BR"), "R$ %.2f", valor)

    private fun nomeDe(forma: FormaPagamento): String = when (forma) {
        FormaPagamento.Dinheiro -> "Dinheiro"
        FormaPagamento.Cartao -> "Cartão"
        is FormaPagamento.Pix -> "Pix"
    }
}
