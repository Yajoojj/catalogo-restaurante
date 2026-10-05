package com.example.catalogorestaurante.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.catalogorestaurante.domain.CalculadoraPedido
import com.example.catalogorestaurante.model.FormaPagamento
import com.example.catalogorestaurante.model.ItemMenu

// Opções do seletor, na ordem do wireframe. O percentual do Pix fica no dado, não na tela.
private val formasPagamentoPadrao: List<FormaPagamento> = listOf(
    FormaPagamento.Dinheiro,
    FormaPagamento.Cartao,
    FormaPagamento.Pix(percentualDesconto = 10.0)
)

/**
 * Tela 2 - Resumo do Pedido.
 *
 * A tela não guarda o pedido e não faz conta: recebe o carrinho e a forma de pagamento
 * por parâmetro, pede os valores para a CalculadoraPedido e avisa a MainActivity
 * pelos callbacks. Quando a forma de pagamento muda lá em cima, a tela recompõe
 * e os valores são recalculados na hora.
 */
@Composable
fun ResumoScreen(
    itens: List<ItemMenu>,
    formaPagamento: FormaPagamento,
    onFormaPagamentoChange: (FormaPagamento) -> Unit,
    onVoltar: () -> Unit,
    modifier: Modifier = Modifier,
    formasDisponiveis: List<FormaPagamento> = formasPagamentoPadrao,
    onFinalizar: () -> Unit = onVoltar
) {
    val subtotal = CalculadoraPedido.subtotal(itens)
    val taxaServico = CalculadoraPedido.taxaServico(itens, formaPagamento)
    val desconto = CalculadoraPedido.desconto(itens, formaPagamento)
    val total = CalculadoraPedido.total(itens, formaPagamento)

    // Único estado da tela: se o aviso de "pedido finalizado" está aberto. É só visual.
    var mostrarConfirmacao by remember { mutableStateOf(false) }

    if (mostrarConfirmacao) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacao = false },
            title = {
                Text("Pedido finalizado", style = MaterialTheme.typography.headlineSmall)
            },
            text = {
                Text(
                    text = "Total: " + CalculadoraPedido.formatarReal(total) +
                            "\nPagamento: " + rotuloDe(formaPagamento),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarConfirmacao = false
                        onFinalizar()
                    }
                ) {
                    Text("OK", style = MaterialTheme.typography.labelLarge)
                }
            }
        )
    }

    // safeDrawingPadding: não deixa o conteúdo ficar embaixo da barra de status
    Column(modifier = modifier.fillMaxSize().safeDrawingPadding().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(
                onClick = onVoltar,
                modifier = Modifier.semantics { contentDescription = "Voltar" }
            ) {
                Text("←", style = MaterialTheme.typography.headlineMedium)
            }
            Text(
                text = "Resumo do Pedido",
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(Modifier.height(8.dp))

        // Parte de cima rola (itens + forma de pagamento); os totais ficam fixos embaixo.
        LazyColumn(modifier = Modifier.weight(1f)) {
            item { Text("ITENS", style = MaterialTheme.typography.titleLarge) }
            if (itens.isEmpty()) {
                item {
                    Text(
                        text = "Nenhum item no carrinho",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
            items(itens) { item ->
                LinhaRecibo(
                    rotulo = item.nome,
                    valor = CalculadoraPedido.formatarReal(item.preco)
                )
            }

            item {
                Spacer(Modifier.height(16.dp))
                Text("FORMA DE PAGAMENTO", style = MaterialTheme.typography.titleLarge)
            }
            items(formasDisponiveis) { forma ->
                OpcaoPagamento(
                    forma = forma,
                    selecionada = forma == formaPagamento,
                    onSelecionar = { onFormaPagamentoChange(forma) }
                )
            }
        }

        HorizontalDivider()
        Spacer(Modifier.height(8.dp))
        LinhaRecibo(rotulo = "Subtotal", valor = CalculadoraPedido.formatarReal(subtotal))
        LinhaRecibo(rotulo = "Taxa de serviço", valor = CalculadoraPedido.formatarReal(taxaServico))
        // Só o Pix tem desconto, então a linha só aparece quando ele está selecionado.
        if (formaPagamento is FormaPagamento.Pix) {
            LinhaRecibo(
                rotulo = "Desconto Pix",
                valor = "-" + CalculadoraPedido.formatarReal(desconto)
            )
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
        LinhaRecibo(
            rotulo = "TOTAL",
            valor = CalculadoraPedido.formatarReal(total),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { mostrarConfirmacao = true },
            enabled = itens.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Finalizar pedido")
        }
    }
}

/** Uma opção do seletor: RadioButton + nome. A linha inteira é clicável. */
@Composable
private fun OpcaoPagamento(
    forma: FormaPagamento,
    selecionada: Boolean,
    onSelecionar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .selectable(
                selected = selecionada,
                onClick = onSelecionar,
                role = Role.RadioButton
            )
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // onClick = null: quem trata o clique é a linha (selectable), não a bolinha
        RadioButton(selected = selecionada, onClick = null)
        Text(
            text = rotuloDe(forma),
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

// when exaustivo: se alguém criar uma forma de pagamento nova, isso aqui para de compilar
// até a tela saber como mostrar. O percentual do Pix vem do próprio objeto, não é fixo.
private fun rotuloDe(forma: FormaPagamento): String = when (forma) {
    FormaPagamento.Dinheiro -> "Dinheiro"
    FormaPagamento.Cartao -> "Cartão"
    is FormaPagamento.Pix -> "Pix – ${forma.percentualDesconto.toInt()}% off"
}
