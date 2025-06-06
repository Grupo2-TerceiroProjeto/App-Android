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
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.R
import com.example.gestok.components.InputLabel
import com.example.gestok.components.orderpage.ItensAdd
import com.example.gestok.components.orderpage.ItensBlock
import com.example.gestok.screens.internalScreens.order.data.OrderCreateData
import com.example.gestok.screens.internalScreens.order.data.OrderItensBlock
import com.example.gestok.screens.internalScreens.order.data.OrderItensCreate
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
fun OrderCreate(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onSucess: () -> Unit,
    viewModel: OrderApiViewModel
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

    val novoPedido = OrderCreateData(
        nomeSolicitante = nomeSolicitante,
        dataEntrega = dataEntrega,
        telefone = telefone.replace(Regex("[^\\d]"), ""),
        status = status,
        produtos = produtos
    )

    var itensAdd by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.limparErrosFormulario()
    }

    LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White),
    verticalArrangement = Arrangement.spacedBy(16.dp),

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
                        stringResource(R.string.order_register_text),
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
                            text = stringResource(R.string.label_order_applicant),
                            value = nomeSolicitante,
                            onValueChange = {
                                val filtered = it.filter { char -> char.isLetter() || char.isWhitespace() }
                                nomeSolicitante = filtered
                            },
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                            erro = viewModel.nomeSolicitanteErro,
                            maxLength = 45
                        )
                    }

                    Column {
                        InputLabel(
                            text = stringResource(R.string.label_order_contact),
                            value = telefone,
                            onValueChange = { telefone = formatPhoneNumber(it) },
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone,
                            erro = viewModel.telefoneErro,
                            maxLength = 15
                        )
                    }

                    Column {
                        SelectOption(
                            text = stringResource(R.string.label_order_status),
                            value = status,
                            onValueChange = { status = it },
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
                            text = stringResource(R.string.label_order_delivery_date),
                            value = dataEntrega,
                            onValueChange = {
                                dataEntrega = formatDate(it)
                            },
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                            erro = viewModel.dataEntregaErro,
                            maxLength = 10
                        )
                    }

                    Column {
                        InputLabel(
                            text =  stringResource(R.string.label_order_value),
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
                        stringResource(R.string.order_items_text),
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
                        Text("  " + stringResource(R.string.to_add_text), color = White)
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


                if (produtos.isEmpty()) {
                    Text(
                        stringResource(R.string.order_empty_products_text),
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
                            .heightIn(max = 250.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(produtos.size) { index ->
                            val item = produtos[index]
                            ItensBlock(
                                listOf(OrderItensBlock(nome = item.nome, quantidade = item.quantidade)),
                                updateQuantidade = { _, newQtd -> updateQuantidade(index, newQtd) },
                                onExcluirClick = {
                                    produtos = produtos.toMutableList().apply {
                                        removeAt(index)
                                    }
                                },
                            )
                        }
                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = { viewModel.salvarPedido(novoPedido, onBack, onSucess) },
                                colors = ButtonDefaults.buttonColors(Blue),
                            ) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = White)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(stringResource(R.string.button_save_text), color = White, fontSize = 16.sp)
                            }

                            if (viewModel.cadastroErro != null) {
                                Text(
                                    viewModel.cadastroErro!!,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.W600,
                                    color = Color(0xFFD32F2F),
                                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                                )
                            }
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
                        produtos = produtos + selectedProducts
                            .filter { selected ->
                                produtos.none { it.nome == selected.nome }
                            }
                            .map {
                                OrderItensCreate(
                                    nome = it.nome,
                                    categoria = it.categoria,
                                    preco = it.preco,
                                    quantidade = 0,
                                    emProducao = it.emProducao,
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