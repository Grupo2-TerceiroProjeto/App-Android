package com.example.gestok.presentation.component.screens.internalScreens.order

import com.example.gestok.presentation.component.SelectOption
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.R
import com.example.gestok.presentation.component.InputLabel
import com.example.gestok.presentation.component.orderpage.ItensAdd
import com.example.gestok.presentation.component.orderpage.ItensBlock
import com.example.gestok.domain.model.order.OrderModel
import com.example.gestok.domain.model.order.OrderEditModel
import com.example.gestok.domain.model.order.OrderItens
import com.example.gestok.domain.model.order.OrderItensBlock
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.White
import com.example.gestok.utils.formatDate
import com.example.gestok.utils.formatPhoneNumber
import com.example.gestok.presentation.viewmodel.order.OrderApiViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun OrderEdit(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    order: OrderModel,
    onSucess: () -> Unit,
    viewModel: OrderApiViewModel
) {

    val originalItens = remember {
        order.produtos.map { it.copy(imagem = it.imagem ?: "") }
    }

    var excluidosItens by remember {
        mutableStateOf<List<OrderItens>>(emptyList())
    }

    var editedNomeSolicitante by remember { mutableStateOf(order.nomeSolicitante) }
    var editedContato by remember { mutableStateOf(order.telefone) }
    var editedStatusPedido by remember { mutableStateOf(order.status) }
    var editedDataEntrega by remember { mutableStateOf(order.dataEntrega ?: "") }
    var editedItens by remember {
        mutableStateOf(
            order.produtos
                .filter { it.quantidade > 0 }
                .map {
                    it.copy(imagem = it.imagem ?: "")
                }
        )
    }
    val editedValorPedido by remember(editedItens) {
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

    val excluidosFiltrados = excluidosItens.filter { excluido ->
        editedItens.none { it.id == excluido.id }
    }

    val pedidoEditado = OrderEditModel(
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

    LaunchedEffect(Unit) {
        viewModel.limparErrosFormulario()
    }

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
                        stringResource(R.string.order_edit_text),
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
                            value = editedNomeSolicitante,
                            onValueChange = {
                                val filtered =
                                    it.filter { char -> char.isLetter() || char.isWhitespace() }
                                editedNomeSolicitante = filtered
                            },
                            keyboardType = KeyboardType.Text,
                            erro = viewModel.nomeSolicitanteErro,
                            maxLength = 45
                        )
                    }

                    Column {
                        InputLabel(
                            text = stringResource(R.string.label_order_contact),
                            value = formatPhoneNumber(editedContato),
                            onValueChange = {
                                val cleaned = it.replace(Regex("[^\\d]"), "").take(11)
                                editedContato = cleaned
                            },
                            keyboardType = KeyboardType.Phone,
                            erro = viewModel.telefoneErro,
                            maxLength = 15
                        )
                    }

                    Column {
                        SelectOption(
                            text = stringResource(R.string.label_order_status),
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
                            text = stringResource(R.string.label_order_delivery_date),
                            value = editedDataEntrega,
                            onValueChange = {
                                editedDataEntrega = formatDate(it)
                            },
                            keyboardType = KeyboardType.Text,
                            erro = viewModel.dataEntregaErro,
                            maxLength = 10
                        )
                    }

                    Column {
                        InputLabel(
                            text =  stringResource(R.string.label_order_value),
                            value = editedValorPedido,
                            onValueChange = { },
                            keyboardType = KeyboardType.Decimal,
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

                if (editedItens.isEmpty()) {
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
                            .heightIn(max  = 250.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(editedItens.size) { index ->
                            val item = editedItens[index]
                            ItensBlock(
                                listOf(
                                    OrderItensBlock(
                                        nome = item.nome,
                                        quantidade = item.quantidade
                                    )
                                ),
                                updateQuantidade = { _, newQtd -> updateQuantidade(index, newQtd) },
                                onExcluirClick = {
                                    val itemRemovido = editedItens[index]
                                    editedItens = editedItens.toMutableList().apply {
                                        removeAt(index)
                                    }

                                    if (originalItens.any { it.id == itemRemovido.id }) {
                                        excluidosItens = excluidosItens + itemRemovido.copy(quantidade = 0)
                                    }
                                }
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
                                onClick = {
                                    viewModel.editarPedido(
                                        pedidoEditado,
                                        excluidosFiltrados,
                                        order.id,
                                        onBack,
                                        onSucess
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(Blue),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = White
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(stringResource(R.string.button_save_text), color = White, fontSize = 16.sp)
                            }

                            if (viewModel.edicaoErro != null) {
                                Text(
                                    viewModel.edicaoErro!!,
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
                        editedItens = editedItens + selectedProducts
                            .filter { selected ->
                                editedItens.none { it.id == selected.id }
                            }
                            .map {
                                OrderItens(
                                    id = it.id,
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