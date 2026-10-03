package com.example.catalogorestaurante.model

sealed interface FormaPagamento {
    object Dinheiro : FormaPagamento
    object Cartao : FormaPagamento
    data class Pix(val percentualDesconto: Double) : FormaPagamento
}
