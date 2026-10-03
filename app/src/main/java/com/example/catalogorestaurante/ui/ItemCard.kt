package com.example.catalogorestaurante.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.catalogorestaurante.model.ItemMenu
import java.util.Locale

@Composable
fun ItemCard(
    item: ItemMenu,
    onAdicionar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.nome, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = item.descricao ?: "Sem descrição",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = String.format(Locale("pt", "BR"), "R$ %.2f", item.preco),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = onAdicionar) { Text("Adicionar") }
        }
    }
}
