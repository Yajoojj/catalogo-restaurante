package com.example.catalogorestaurante.model

sealed class ItemMenu(
    val nome: String,
    val preco: Double,
    val descricao: String? = null
)

class Prato(
    nome: String,
    preco: Double,
    descricao: String? = null,
    val vegetariano: Boolean
) : ItemMenu(nome, preco, descricao)

class Bebida(
    nome: String,
    preco: Double,
    descricao: String? = null,
    val alcoolica: Boolean
) : ItemMenu(nome, preco, descricao)
