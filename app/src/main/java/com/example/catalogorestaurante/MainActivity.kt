package com.example.catalogorestaurante

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.catalogorestaurante.domain.CalculadoraPedido
import com.example.catalogorestaurante.model.CardapioData
import com.example.catalogorestaurante.model.FormaPagamento
import com.example.catalogorestaurante.model.ItemMenu
import com.example.catalogorestaurante.ui.CatalogoScreen
import com.example.catalogorestaurante.ui.ResumoScreen

private const val TAG = "Pedido"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val carrinho = remember { mutableStateListOf<ItemMenu>() }
                var formaPagamento by remember {
                    mutableStateOf<FormaPagamento>(FormaPagamento.Dinheiro)
                }
                var noResumo by remember { mutableStateOf(false) }

                if (noResumo) {
                    BackHandler { noResumo = false }
                    ResumoScreen(
                        itens = carrinho,
                        formaPagamento = formaPagamento,
                        onFormaPagamentoChange = {
                            formaPagamento = it
                            Log.d(TAG, CalculadoraPedido.gerarRelatorio(carrinho, it))
                        },
                        onVoltar = { noResumo = false }
                    )
                } else {
                    CatalogoScreen(
                        itens = CardapioData.itens,
                        quantidadeCarrinho = carrinho.size,
                        onAdicionar = { carrinho.add(it) },
                        onVerResumo = {
                            Log.d(TAG, CalculadoraPedido.gerarRelatorio(carrinho, formaPagamento))
                            noResumo = true
                        }
                    )
                }
            }
        }
    }
}
