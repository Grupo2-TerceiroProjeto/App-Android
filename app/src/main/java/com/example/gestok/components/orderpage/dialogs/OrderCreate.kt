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
import com.example.gestok.screens.internalScreens.order.data.OrderCreateData
import com.example.gestok.screens.internalScreens.order.data.OrderItensBlock
import com.example.gestok.screens.internalScreens.order.data.OrderItensCreate
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.White
import com.example.gestok.utils.formatDate
import com.example.gestok.viewModel.order.OrderApiViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun OrderCreate(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {

    var nomeSolicitante by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Selecione uma opção") }
    var dataEntrega by remember { mutableStateOf("") }
    var produtos by remember { mutableStateOf(emptyList<OrderItensCreate>()) }
    val totalCompra by remember(produtos) {
        mutableStateOf(
            NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
                .format(produtos.sumOf { it.preco * it.quantidade })
        )
    }

    val updateQuantidade: (Int, Int) -> Unit = { index, newQuantidade ->
        produtos = produtos.toMutableList().apply {
            this[index] = this[index].copy(quantidade = newQuantidade)
        }
    }

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val dataEntregaParsed = runCatching {
        if (dataEntrega.isNotBlank()) LocalDate.parse(dataEntrega, formatter)
        else null
    }.getOrNull()

    val novoPedido = OrderCreateData(
        nomeSolicitante = nomeSolicitante,
        dataEntrega = dataEntregaParsed,
        telefone = telefone,
        status = status,
        produtos = produtos
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
                        "Cadastrar Pedido",
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
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {

                    Column {
                        InputLabel(
                            text = "Solicitante",
                            value = nomeSolicitante,
                            onValueChange = {
                                val filtered = it.filter { char -> char.isLetter() || char.isWhitespace() }
                                nomeSolicitante = filtered
                            },
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                            maxLength = 45
                        )
                    }

                    Column {
                        InputLabel(
                            text = "Contato",
                            value = telefone,
                            onValueChange = { telefone = it },
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone,
                            maxLength = 11
                        )
                    }

                    Column {
                        SelectOption(
                            text = "Status do Pedido",
                            value = status,
                            onValueChange = { status = it },
                            list = listOf(
                                "Pendente",
                                "Em Produção",
                                "Concluído",
                                "Cancelado"
                            )
                        )
                    }

                    Column {
                        InputLabel(
                            text = "Data de Entrega",
                            value = dataEntrega,
                            onValueChange = {
                                dataEntrega = formatDate(it)
                            },
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                            maxLength = 10
                        )
                    }

                    Column {
                        InputLabel(
                            text = "Valor",
                            value = totalCompra,
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

                if (produtos.isEmpty()) {
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
                        ItensBlock(produtos.map { item ->
                            OrderItensBlock(
                                nome = item.nome,
                                quantidade = item.quantidade
                            )
                        }, updateQuantidade)
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
                val viewModel: OrderApiViewModel = koinViewModel()

                ItensAdd(
                    viewModel,
                    onConfirm = { selectedProducts ->
                        produtos = produtos + selectedProducts.map {
                            OrderItensCreate(
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