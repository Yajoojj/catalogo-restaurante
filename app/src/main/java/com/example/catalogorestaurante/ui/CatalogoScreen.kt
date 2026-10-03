package com.example.catalogorestaurante.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.catalogorestaurante.model.Bebida
import com.example.catalogorestaurante.model.ItemMenu
import com.example.catalogorestaurante.model.Prato

@Composable
fun CatalogoScreen(
    itens: List<ItemMenu>,
    quantidadeCarrinho: Int,
    onAdicionar: (ItemMenu) -> Unit,
    onVerResumo: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pratos = itens.filterIsInstance<Prato>()
    val bebidas = itens.filterIsInstance<Bebida>()

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Cardápio", style = MaterialTheme.typography.headlineMedium)
            Text("Carrinho: $quantidadeCarrinho", style = MaterialTheme.typography.titleMedium)
        }
        Spacer(Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { Text("PRATOS", style = MaterialTheme.typography.titleLarge) }
            items(pratos) { ItemCard(item = it, onAdicionar = { onAdicionar(it) }) }
            item { Text("BEBIDAS", style = MaterialTheme.typography.titleLarge) }
            items(bebidas) { ItemCard(item = it, onAdicionar = { onAdicionar(it) }) }
        }

        Spacer(Modifier.height(8.dp))
        Button(onClick = onVerResumo, modifier = Modifier.fillMaxWidth()) {
            Text("Ver resumo")
        }
    }
}
