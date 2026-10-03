package com.example.catalogorestaurante.model

object CardapioData {
    val itens: List<ItemMenu> = listOf(
        Prato(
            nome = "Pizza Margherita",
            preco = 42.00,
            descricao = "Molho de tomate, mussarela e manjericão fresco.",
            vegetariano = true
        ),
        Prato(
            nome = "Feijoada completa",
            preco = 58.00,
            descricao = "Feijão preto cozido lentamente com carne seca, costelinha, " +
                    "linguiça calabresa, paio e bacon, acompanhada de arroz branco, couve " +
                    "refogada, farofa crocante, torresmo e laranja fatiada. Serve bem uma " +
                    "pessoa com fome ou duas pessoas que queiram só beliscar.",
            vegetariano = false
        ),
        Prato(
            nome = "Salada Caesar",
            preco = 32.00,
            descricao = null, // item sem descrição, para testar o layout
            vegetariano = false
        ),
        Bebida(
            nome = "Suco de laranja",
            preco = 12.00,
            descricao = "Natural, 300 ml.",
            alcoolica = false
        ),
        Bebida(
            nome = "Cerveja artesanal",
            preco = 18.00,
            descricao = null,
            alcoolica = true
        )
    )
}
