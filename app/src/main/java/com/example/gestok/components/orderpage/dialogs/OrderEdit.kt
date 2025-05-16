package com.example.gestok.components.orderpage.dialogs

import SelectOption
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.components.InputLabel
import com.example.gestok.screens.internalScreens.order.data.OrderData
import com.example.gestok.screens.internalScreens.order.data.OrderItens
import com.example.gestok.screens.internalScreens.order.data.OrderItensBlock
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.White
import com.example.gestok.viewModel.order.OrderApiViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrderEdit(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    order: OrderData
) {

    var editedNomeSolicitante by remember { mutableStateOf(order.nomeSolicitante) }
    var editedContato by remember { mutableStateOf(order.telefone) }
    var editedStatusPedido by remember { mutableStateOf(order.status) }
    var editedDataEntrega by remember { mutableStateOf(order.dataEntrega ?: "") }
    var editedValorPedido by remember { mutableStateOf(order.totalCompra.toString()) }
    var editedItens by remember {
        mutableStateOf(
            order.produtos.map { item ->
                OrderItensBlock(
                    nome = item.nome,
                    quantidade = item.quantidade
                )
            }
        )
    }

    val updateQuantidade: (Int, Int) -> Unit = { index, newQuantidade ->
        editedItens = editedItens.toMutableList().apply {
            this[index] = this[index].copy(quantidade = newQuantidade)
        }
    }

    var itensAdd by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(White),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {

        item {
            Row(
                Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, top = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFF005BA4), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }

                Text(
                    "Editar Pedido",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,
                    color = Black,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                )
            }
        }

        item {
            InputLabel(
                text = "Solicitante",
                value = editedNomeSolicitante,
                onValueChange = { editedNomeSolicitante = it },
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                maxLength = 45
            )
        }

        item {
            InputLabel(
                text = "Contato",
                value = editedContato,
                onValueChange = { editedContato = it },
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone,
                maxLength = 11
            )
        }

        item {
            SelectOption(
                text = "Status do Pedido",
                value = editedStatusPedido,
                onValueChange = { editedStatusPedido = it },
                list = listOf(
                    "Pendente",
                    "Em Produção",
                    "Concluído",
                    "Cancelado"
                )
            )
        }

        item {
            InputLabel(
                text = "Data de Entrega",
                value = editedDataEntrega,
                onValueChange = { editedDataEntrega = it },
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                maxLength = 10
            )
        }

        item {
            InputLabel(
                text = "Valor",
                value = editedValorPedido,
                onValueChange = { editedValorPedido = it },
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal,
                maxLength = 15
            )
        }

        if (!itensAdd) {
            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp, start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Itens",
                        fontWeight = W600,
                        color = Blue,
                        fontSize = 18.sp
                    )
                    Button(
                        onClick = { itensAdd = true },
                        colors = ButtonDefaults.buttonColors(LightBlue)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = White,
                        )
                        Text("  Adicionar", color = White)
                    }
                }

                if (editedItens.isEmpty()) {

                        Text(
                            "Para salvar o pedido, é necessário adicionar pelo menos um produto",
                            fontSize = 14.sp,
                            color = Black,
                            modifier = Modifier.padding(
                                start = 50.dp,
                                end = 50.dp,
                                top = 32.dp,
                                bottom = 32.dp
                            ),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                } else {

                        Column(Modifier.padding(start = 20.dp, end = 20.dp, top = 24.dp)) {
                            ItensBlock(editedItens, updateQuantidade)
                        }

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 10.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = { },
                                colors = ButtonDefaults.buttonColors(Blue),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = White
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Salvar", color = White, fontSize = 16.sp)
                            }
                        }

                }
            }
        }

        if (itensAdd) {
            item {
                val viewModel: OrderApiViewModel = koinViewModel()

                ItensAdd(
                    viewModel = viewModel,
                    onConfirm = { selectedProducts ->
                        editedItens = editedItens + selectedProducts.map {
                            OrderItensBlock(nome = it.nome, quantidade = 0)
                        }
                        itensAdd = false
                    }
                )
            }
        }
    }
}
