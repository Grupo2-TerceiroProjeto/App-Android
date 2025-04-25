package com.example.gestok.components.productpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.components.productpage.dialogs.AdicionarAoEstoque
import com.example.gestok.components.productpage.dialogs.ProductCreateDialog

import com.example.gestok.components.productpage.dialogs.ProductEdit
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.White

@Composable
fun ProductContent(modifier: Modifier = Modifier, produtosLista: List<ProductData>) {

    var showEditDialog by remember { mutableStateOf(false) }
    var showStockAddDialog by remember { mutableStateOf(false) }
    var selectedProductForStock by remember { mutableStateOf<ProductData?>(null) }
    var showCreateDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LightGray)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Produtos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        color = Black
                    )

                    Button(
                        onClick = {

                            if (produtosLista.isNotEmpty()) {
                                selectedProductForStock = produtosLista.first()
                                showStockAddDialog = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Blue)
                    ) {
                        Text("Add Estoque", color = White)
                    }

                    Button(
                        onClick = { showCreateDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Blue)
                    ) {
                        Text("Novo produto", color = White)
                    }
                }
            }
        }

        items(items = produtosLista){ produto ->

            ProductData(produto)

        }
    }


    if(showCreateDialog){
        ProductCreateDialog(
            onDismiss = { showCreateDialog = false },
            onConfirm = { newProduto, newEstoque, newCategoria, newValor, newIngredientes ->
                showCreateDialog = false
            }
        )
    }

    if (showEditDialog) {
         ProductEdit(
            product = produtosLista.first(),
            onDismiss = { showCreateDialog = false },
            onConfirm = { newProduto, newEstoque, newCategoria, newValor, newIngredientes ->
                        showCreateDialog = false
            }
        )
    }

    if (showStockAddDialog) {
        AdicionarAoEstoque(
            produtos = produtosLista,
            onDismiss = {
                showStockAddDialog = false
                selectedProductForStock = null
            },
            onConfirm = { produto ->
                showStockAddDialog = false
                selectedProductForStock = null
            }
        )
    }
}


