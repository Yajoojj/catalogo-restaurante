package com.example.catalogorestaurante

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.catalogorestaurante.model.CardapioData
import com.example.catalogorestaurante.ui.CatalogoScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                var qtd by remember { mutableIntStateOf(0) }
                CatalogoScreen(
                    itens = CardapioData.itens,
                    quantidadeCarrinho = qtd,
                    onAdicionar = { qtd++ },
                    onVerResumo = { }
                )
            }
        }
    }
}