package com.example.gestok.presentation.component.screens.internalScreens.product

import com.example.gestok.presentation.component.SelectOption
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.gestok.R
import com.example.gestok.presentation.component.InputLabel
import com.example.gestok.presentation.component.productpage.IngredientAdd
import com.example.gestok.presentation.component.productpage.IngredientBlockExclude
import com.example.gestok.presentation.component.productpage.IngredientCreate
import com.example.gestok.presentation.component.productpage.IngredientsEdit
import com.example.gestok.domain.model.product.IngredientsBlock
import com.example.gestok.domain.model.product.IngredientsModel
import com.example.gestok.domain.model.product.IngredientsRecipe
import com.example.gestok.domain.model.product.ProductModel
import com.example.gestok.domain.model.product.ProductStepEditModel
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.MediumGray
import com.example.gestok.ui.theme.White
import com.example.gestok.presentation.viewmodel.product.ProductApiViewModel
import java.io.File

@Composable
fun ProductEdit(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    product: ProductModel,
    onSucess: () -> Unit,
    viewModel: ProductApiViewModel
) {

    val context = LocalContext.current
    var imagemUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    var publicId by remember { mutableStateOf<String?>(null) }

    fun Uri.toFile(context: Context): File {
        val contentResolver = context.contentResolver
        val mime = contentResolver.getType(this)
        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mime) ?: "jpg"

        val inputStream = contentResolver.openInputStream(this)!!
        val tempFile = File.createTempFile("upload_", ".$extension", context.cacheDir)
        tempFile.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        return tempFile
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imagemUri = uri
        }

    LaunchedEffect(imagemUri) {
        imagemUri?.let {
            isUploading = true
            val file = it.toFile(context)
            val id = viewModel.uploadImagem(file)
            publicId = id
            isUploading = false
        }
    }

    val imagemUrl by remember(product.imagem, publicId) {
        mutableStateOf(
            viewModel.getImagem(publicId ?: product.imagem)
        )
    }

    var nome by remember { mutableStateOf(product.nome) }
    var preco by remember { mutableDoubleStateOf(product.preco) }
    var estoque by remember { mutableIntStateOf(product.quantidade) }
    var categoria by remember { mutableIntStateOf(product.categoria) }
    var subCategoria by remember { mutableIntStateOf(product.categoria) }
    var ingredientes by remember { mutableStateOf(emptyList<IngredientsRecipe>()) }

    var precoTexto by remember { mutableStateOf(product.preco.toString()) }
    var estoqueTexto by remember { mutableStateOf(product.quantidade.toString()) }

    val categorias = viewModel.categorias

    val updateQuantidade: (Int, Int) -> Unit = { index, newQuantidade ->
        ingredientes = ingredientes.toMutableList().apply {
            this[index] = this[index].copy(quantidade = newQuantidade.toDouble())
        }
    }

    val produtoEditado = ProductStepEditModel(
        id = product.id,
        nome = nome,
        categoria = subCategoria,
        preco = preco,
        quantidade = estoque,
        imagem = publicId ?: product.imagem,
        emProducao = product.emProducao,
        ingredientes = ingredientes
    )

    var itensAdd by remember { mutableStateOf(false) }
    var criandoIngrediente by remember { mutableStateOf(false) }
    var editandoIngrediente by remember { mutableStateOf(false) }
    var ingredienteEditado by remember { mutableStateOf<IngredientsModel?>(null) }
    val carregandoReceita = viewModel.carregouReceita

    LaunchedEffect(Unit) {
        viewModel.limparErrosFormulario()
        viewModel.getCategorias()
        viewModel.getReceita(product) { listaIngredientes ->
            ingredientes = listaIngredientes
        }
    }

    LaunchedEffect(viewModel.ingredientes) {
        viewModel.getReceita(product) { listaAtualizada ->
            ingredientes = listaAtualizada
        }
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
                        stringResource(R.string.title_edit_product),
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
                        Text(
                            stringResource(R.string.label_product_photo),
                            Modifier.padding(bottom = 15.dp),
                            fontWeight = W600,
                            color = Blue
                        )

                        Column(
                            Modifier.fillMaxWidth(),
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10))
                                    .fillMaxWidth()
                                    .height(280.dp)
                                    .background(LightGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10))
                                        .fillMaxWidth()
                                        .height(280.dp)
                                        .background(LightGray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    when {
                                        isUploading -> {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Text(stringResource(R.string.loading_product_photo), color = Blue)
                                            }
                                        }
                                        imagemUrl != null -> {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(280.dp)
                                                    .clip(RoundedCornerShape(10))
                                            ) {
                                                AsyncImage(
                                                    model = imagemUrl,
                                                    contentDescription = null,
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .clip(RoundedCornerShape(10))
                                                )
                                                Button(
                                                    onClick = { launcher.launch("image/*") },
                                                    colors = ButtonDefaults.buttonColors(LightBlue),
                                                    modifier = Modifier
                                                        .align(Alignment.BottomCenter)
                                                        .padding(bottom = 12.dp)
                                                ) {
                                                    Text(stringResource(R.string.button_product_change_image), color = White)
                                                }
                                            }
                                        }
                                        else -> {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Icon(
                                                    imageVector = Icons.Default.ArrowCircleUp,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(100.dp),
                                                    tint = Blue
                                                )
                                                Spacer(modifier = Modifier.height(50.dp))
                                                Button(
                                                    onClick = { launcher.launch("image/*") },
                                                    colors = ButtonDefaults.buttonColors(Blue)
                                                ) {
                                                    Text(stringResource(R.string.button_product_choose_image), color = White)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Column {
                        InputLabel(
                            text = stringResource(R.string.label_name),
                            value = nome,
                            onValueChange = {
                                val filtered =
                                    it.filter { char -> char.isLetter() || char.isWhitespace() }
                                nome = filtered
                            },
                            keyboardType = KeyboardType.Text,
                            erro = viewModel.nomeErro,
                            maxLength = 45,
                        )
                    }

                    Column {
                        InputLabel(
                            text = stringResource(R.string.label_product_price),
                            value = precoTexto,
                            onValueChange = {
                                precoTexto = it
                                preco = it.toDoubleOrNull() ?: 0.0
                            },
                            keyboardType = KeyboardType.Decimal,
                            erro = viewModel.precoErro,
                            maxLength = 15
                        )
                    }

                    Column {
                        InputLabel(
                            text = stringResource(R.string.label_product_stock),
                            value = estoqueTexto,
                            onValueChange = {
                                estoqueTexto = it
                                estoque = it.toIntOrNull() ?: 0
                            },
                            keyboardType = KeyboardType.Number,
                            erro = viewModel.estoqueErro,
                            maxLength = 15
                        )
                    }

                    Column {
                        val categoriaAtual = categorias.find { it.id == categoria }

                        val categoriasUnicas = categorias.distinctBy { it.nome }

                        val categoriaSelecionada = categoriaAtual?.nome ?: ""

                        SelectOption(
                            text = stringResource(R.string.label_product_categories),
                            value = categoriaSelecionada,
                            onValueChange = { selectedNome ->
                                val idSelecionado = categoriasUnicas.find { it.nome == selectedNome }?.id
                                categoria = idSelecionado ?: 0
                            },
                            list = categoriasUnicas.map { it.nome },
                            erro = viewModel.categoriaErro
                        )
                    }

                    Column {
                        val subCategoriaSelecionada =
                            categorias.find { it.id == subCategoria }?.subCategoria ?: ""

                        SelectOption(
                            text = stringResource(R.string.label_product_sub_categories),
                            value = subCategoriaSelecionada,
                            onValueChange = { selectedNome ->
                                val idSelecionado =
                                    categorias.find { it.subCategoria == selectedNome }?.id
                                subCategoria = idSelecionado ?: 0
                            },
                            list = categorias.map { it.subCategoria },
                            erro = viewModel.subCategoriaErro
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
                        stringResource(R.string.product_ingredients_text),
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

                when {
                    !carregandoReceita -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .wrapContentSize(Alignment.Center)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator()
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(stringResource(R.string.loading_product_recipe), color = MediumGray)
                            }
                        }
                    }

                    ingredientes.isEmpty() -> {
                        Text(
                            stringResource(R.string.empty_products_msg),
                            fontSize = 14.sp,
                            color = Black,
                            modifier = Modifier.padding(
                                start = 50.dp,
                                end = 50.dp,
                                top = 32.dp,
                                bottom = 32.dp
                            ),
                            textAlign = TextAlign.Center
                        )
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier
                                .padding(start = 20.dp, end = 20.dp, top = 24.dp)
                                .heightIn(max = 240.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(ingredientes.size) { index ->
                                val item = ingredientes[index]
                                IngredientBlockExclude(
                                    listOf(
                                        IngredientsBlock(
                                            nome = item.nome,
                                            quantidade = item.quantidade.toInt()
                                        )
                                    ),
                                    updateQuantidade = { _, newQtd ->
                                        updateQuantidade(
                                            index,
                                            newQtd
                                        )
                                    },
                                    onExcluirClick = {
                                        if(item.id == 0) {
                                            ingredientes = ingredientes.toMutableList().apply {
                                                removeAt(index)
                                            }
                                        }else {
                                            viewModel.deletarReceita(item.id) { sucesso ->
                                                if (sucesso) {
                                                    ingredientes =
                                                        ingredientes.toMutableList().apply {
                                                            removeAt(index)
                                                        }
                                                }

                                            }
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
                                    onClick = { viewModel.editarProduto(produtoEditado, onBack, onSucess) },
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
        }

        if (criandoIngrediente) {
            item {
                IngredientCreate(
                    viewModel,
                    onBack = {
                        criandoIngrediente = false
                    },
                    product.id
                )

            }
        } else if (editandoIngrediente && ingredienteEditado != null) {
            item {
                IngredientsEdit(
                    viewModel = viewModel,
                    onBack = {
                        editandoIngrediente = false
                        ingredienteEditado = null
                    },
                    ingrediente = ingredienteEditado!!
                )
            }
        } else if (itensAdd) {
                item {

                IngredientAdd(
                    viewModel,
                    onConfirm = { selectedIngredients ->
                        val novosIngredientes = selectedIngredients.filter { selected ->
                            ingredientes.none { existing -> existing.idIngrediente == selected.id }
                        }.map {
                            IngredientsRecipe(
                                id = 0,
                                idIngrediente = it.id,
                                nome = it.nome,
                                medida = it.medida,
                                quantidade = 0.0
                            )
                        }

                        ingredientes = ingredientes + novosIngredientes

                        itensAdd = false
                    },
                    onCriarNovoIngrediente = {
                        criandoIngrediente = true
                    },
                    onEditarIngrediente = {
                        ingredienteEditado = it
                        editandoIngrediente = true
                    }
                )
            }
        }

    }
}