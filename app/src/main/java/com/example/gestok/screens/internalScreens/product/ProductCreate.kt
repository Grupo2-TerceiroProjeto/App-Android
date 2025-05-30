package com.example.gestok.screens.internalScreens.product

import SelectOption
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.components.InputLabel
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.White
import com.example.gestok.viewModel.product.ProductApiViewModel
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.example.gestok.components.productpage.IngredientAdd
import com.example.gestok.components.productpage.IngredientBlock
import com.example.gestok.components.productpage.IngredientCreate
import com.example.gestok.screens.internalScreens.product.data.IngredientsBlock
import com.example.gestok.screens.internalScreens.product.data.IngredientsProduct
import com.example.gestok.screens.internalScreens.product.data.ProductStepData
import java.io.File


@Composable
fun ProductCreate(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
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

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
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

    var nome by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf(0.0) }
    var estoque by remember { mutableStateOf(0) }
    var categoria by remember { mutableStateOf(0) }
    var subCategoria by remember { mutableStateOf(0) }
    var ingredientes by remember { mutableStateOf(emptyList<IngredientsProduct>()) }

    var precoTexto by remember { mutableStateOf("") }
    var estoqueTexto by remember { mutableStateOf("") }

    val categorias = viewModel.categorias

    val updateQuantidade: (Int, Int) -> Unit = { index, newQuantidade ->
        ingredientes = ingredientes.toMutableList().apply {
            this[index] = this[index].copy(quantidade = newQuantidade.toDouble())
        }
    }

    val novoProduto = ProductStepData (
        nome = nome,
        categoria = categoria,
        subCategoria = subCategoria,
        preco = preco,
        quantidade = estoque,
        imagem = publicId ?: "",
        emProducao = true,
        ingredientes = ingredientes
    )

    var itensAdd by remember { mutableStateOf(false) }
    var criandoIngrediente by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.limparErrosFormulario()
        viewModel.getCategorias()
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
                        "Cadastrar Produto",
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
                            "Foto do produto",
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
                                if (isUploading) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("Carregando...", color = Blue)
                                    }
                                } else if (publicId != null) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(280.dp)
                                            .clip(RoundedCornerShape(10))
                                    ) {
                                        AsyncImage(
                                            model = imagemUri,
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
                                            Text("Trocar Imagem", color = White)
                                        }
                                    }
                                } else {
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
                                            Text("Escolher Imagem", color = White)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Column {
                        InputLabel(
                            text = "Nome",
                            value = nome,
                            onValueChange = {
                                val filtered = it.filter { char -> char.isLetter() || char.isWhitespace() }
                                nome = filtered
                            },
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                            erro = viewModel.nomeErro,
                            maxLength = 45,
                        )
                    }

                    Column {
                        InputLabel(
                            text = "Preço",
                            value = precoTexto,
                            onValueChange = {
                                precoTexto = it
                                preco = it.toDoubleOrNull() ?: 0.0
                            },
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal,
                            erro = viewModel.precoErro,
                            maxLength = 15
                        )
                    }

                    Column {
                        InputLabel(
                            text = "Estoque",
                            value = estoqueTexto,
                            onValueChange = {
                                estoqueTexto = it
                                estoque = it.toIntOrNull() ?: 0
                            },
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                            erro = viewModel.estoqueErro,
                            maxLength = 15
                        )
                    }

                    Column {
                        val categoriasUnicas = categorias.distinctBy { it.nome }
                        val categoriaSelecionada = categoriasUnicas.find { it.id == categoria }?.nome ?: ""

                        SelectOption(
                            text = "Categoria",
                            value = if (categoriaSelecionada.isEmpty()) "Selecione uma opção" else categoriaSelecionada,
                            onValueChange = { selectedNome ->
                                val idSelecionado = categoriasUnicas.find { it.nome == selectedNome }?.id
                                categoria = idSelecionado ?: 0
                            },
                            list = categoriasUnicas.map { it.nome },
                            erro = viewModel.categoriaErro
                        )
                    }

                    Column {
                        var subCategoriaSelecionada by remember { mutableStateOf("") }

                        SelectOption(
                            text = "Sub Categoria",
                            value = if (subCategoriaSelecionada.isEmpty()) "Selecione uma opção" else subCategoriaSelecionada,
                            onValueChange = { selectedNome ->
                                subCategoriaSelecionada = selectedNome
                                val idSelecionado = categorias.find { it.subCategoria == selectedNome }?.id
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
                        "Ingredientes",
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


                if (ingredientes.isEmpty()) {
                    Text(
                        "Para salvar o produto, é necessário adicionar pelo menos um ingrediente",
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
                        items(ingredientes.size) { index ->
                            val item = ingredientes[index]
                            IngredientBlock(
                                listOf(IngredientsBlock(nome = item.nome, quantidade = item.quantidade.toInt())),
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
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = { viewModel.salvarProduto(novoProduto, onBack, onSucess) },
                                colors = ButtonDefaults.buttonColors(Blue),
                            ) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = White)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Salvar", color = White, fontSize = 16.sp)
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

        if (criandoIngrediente) {
            item {
                IngredientCreate(
                    onSalvar = {
                        criandoIngrediente = false
                    }
                )

            }
        } else if (itensAdd) {
            item {

                IngredientAdd(
                    viewModel,
                    onConfirm = { selectedIngredients ->
                        ingredientes = ingredientes + selectedIngredients.map {
                            IngredientsProduct(
                                id = it.id,
                                nome = it.nome,
                                quantidade = 0.0
                            )
                        }
                        itensAdd = false

                    },
                    onCriarNovoIngrediente = {
                        criandoIngrediente = true
                    }
                )
            }
        }

    }

}