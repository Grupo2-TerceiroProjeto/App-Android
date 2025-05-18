package com.example.gestok.screens.internalScreens.order

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
import com.example.gestok.components.orderpage.ItensAdd
import com.example.gestok.components.orderpage.ItensBlock
import com.example.gestok.screens.internalScreens.order.data.OrderData
import com.example.gestok.screens.internalScreens.order.data.OrderEditData
import com.example.gestok.screens.internalScreens.order.data.OrderItens
import com.example.gestok.screens.internalScreens.order.data.OrderItensBlock
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.White
import com.example.gestok.utils.formatDate
import com.example.gestok.utils.formatPhoneNumber
import com.example.gestok.viewModel.order.OrderApiViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun OrderEdit(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    order: OrderData,
    onSucess: () -> Unit,
    viewModel: OrderApiViewModel
) {

    var editedNomeSolicitante by remember { mutableStateOf(order.nomeSolicitante) }
    var editedContato by remember { mutableStateOf(order.telefone) }
    var editedStatusPedido by remember { mutableStateOf(order.status) }
    var editedDataEntrega by remember { mutableStateOf(order.dataEntrega ?: "") }
    var editedItens by remember {
        mutableStateOf(order.produtos.map {
            it.copy(imagem = it.imagem ?: "")
        })
    }
    val editedValorPedido by remember (editedItens) {
        mutableStateOf(
            NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
                .format(editedItens.sumOf { it.preco * it.quantidade })
        )
    }

    val updateQuantidade: (Int, Int) -> Unit = { index, newQuantidade ->
        editedItens = editedItens.toMutableList().apply {
            this[index] = this[index].copy(quantidade = newQuantidade)
        }
    }

    val pedidoEditado = OrderEditData(
        nomeSolicitante = editedNomeSolicitante,
        dataEntrega = editedDataEntrega,
        telefone = editedContato,
        status = editedStatusPedido,
        produtos = editedItens.map { item ->
            OrderItensBlock(
                nome = item.nome,
                quantidade = item.quantidade
            )
        }
    )

    var itensAdd by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(24.dp),

        ) {

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
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

                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {

                    Column {
                        InputLabel(
                            text = "Solicitante",
                            value = editedNomeSolicitante,
                            onValueChange = {
                                val filtered = it.filter { char -> char.isLetter() || char.isWhitespace() }
                                editedNomeSolicitante = filtered
                            },
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                            erro = viewModel.nomeSolicitanteErro,
                            maxLength = 45
                        )
                    }

                    Column {
                        InputLabel(
                            text = "Contato",
                            value = formatPhoneNumber(editedContato),
                            onValueChange = {
                                val cleaned = it.replace(Regex("[^\\d]"), "").take(11)
                                editedContato = cleaned
                            },
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone,
                            erro = viewModel.telefoneErro,
                            maxLength = 15
                        )
                    }

                    Column {
                        SelectOption(
                            text = "Status do Pedido",
                            value = editedStatusPedido,
                            onValueChange = { editedStatusPedido = it },
                            list = listOf(
                                "Pendente",
                                "Em Produção",
                                "Concluído",
                                "Cancelado"
                            ),
                            erro = viewModel.statusErro
                        )
                    }

                    Column {
                        InputLabel(
                            text = "Data de Entrega",
                            value = editedDataEntrega,
                            onValueChange = {
                                editedDataEntrega = formatDate(it)
                            },
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                            erro = viewModel.dataEntregaErro,
                            maxLength = 10
                        )
                    }

                    Column {
                        InputLabel(
                            text = "Valor",
                            value = editedValorPedido,
                            onValueChange = { },
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal,
                            readOnly = true,
                            maxLength = 15
                        )
                    }

                }

            }

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

                if (viewModel.itensErro != null) {
                    Text(
                        viewModel.itensErro!!,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W600,
                        color = Color(0xFFD32F2F),
                        modifier = Modifier.padding(
                            start = 20.dp,
                            top = 32.dp
                        ),
                    )
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
                    LazyColumn(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 24.dp)
                            .height(250.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(editedItens.size) { index ->
                            val item = editedItens[index]
                            ItensBlock(
                                listOf(OrderItensBlock(nome = item.nome, quantidade = item.quantidade)),
                                updateQuantidade = { _, newQtd -> updateQuantidade(index, newQtd) }
                            )
                        }
                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { viewModel.editarPedido(pedidoEditado, order.id, onBack, onSucess) },
                            colors = ButtonDefaults.buttonColors(Blue),
                        ) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Salvar", color = White,  fontSize = 16.sp)
                        }
                    }
                }
            }
        }

        if (itensAdd) {
            item {

                ItensAdd(
                    viewModel,
                    onConfirm = { selectedProducts ->
                        editedItens = editedItens + selectedProducts.map {
                            OrderItens(
                                id = it.id_produto,
                                nome = it.nome,
                                categoria = it.fk_categoria,
                                preco = it.preco,
                                quantidade = 0,
                                emProducao = it.em_producao,
                                imagem = it.imagem ?: "",
                            )
                        }
                        itensAdd = false

                    }
                )
            }
        }

    }

}