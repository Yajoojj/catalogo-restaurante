package com.example.catalogorestaurante.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.catalogorestaurante.domain.CalculadoraPedido
import com.example.catalogorestaurante.model.FormaPagamento
import com.example.catalogorestaurante.model.ItemMenu

// TODO tela provisória só pra navegação funcionar, o layout do recibo é da #3
@Composable
fun ResumoScreen(
    itens: List<ItemMenu>,
    formaPagamento: FormaPagamento,
    onFormaPagamentoChange: (FormaPagamento) -> Unit,
    onVoltar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        TextButton(onClick = onVoltar) { Text("Voltar") }
        Text("Resumo do Pedido", style = MaterialTheme.typography.headlineMedium)
        Text(
            text = "Total: " + CalculadoraPedido.formatarReal(
                CalculadoraPedido.total(itens, formaPagamento)
            ),
            style = MaterialTheme.typography.titleLarge
        )
    }
}
