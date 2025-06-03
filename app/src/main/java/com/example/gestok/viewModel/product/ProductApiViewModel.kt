package com.example.gestok.viewModel.product

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.viewModelScope
import com.example.gestok.network.service.CloudinaryService
import com.example.gestok.network.service.LingvaService
import com.example.gestok.network.service.ProductService
import com.example.gestok.network.service.SpoonacularService
import com.example.gestok.screens.internalScreens.order.data.RecipeBody
import com.example.gestok.screens.internalScreens.order.data.RecipeData
import com.example.gestok.screens.internalScreens.product.data.CategoryData
import com.example.gestok.screens.internalScreens.product.data.Ingrediente
import com.example.gestok.screens.internalScreens.product.data.IngredienteComIdSpoonacular
import com.example.gestok.screens.internalScreens.product.data.IngredienteComNutrientes
import com.example.gestok.screens.internalScreens.product.data.IngredienteTraduzido
import com.example.gestok.screens.internalScreens.product.data.IngredientsBody
import com.example.gestok.screens.internalScreens.product.data.IngredientsData
import com.example.gestok.screens.internalScreens.product.data.IngredientsProduct
import com.example.gestok.screens.internalScreens.product.data.IngredientsRecipe
import com.example.gestok.screens.internalScreens.product.data.Nutriente
import com.example.gestok.screens.internalScreens.product.data.NutrientesBody
import com.example.gestok.screens.internalScreens.product.data.NutrientesResponse
import com.example.gestok.screens.internalScreens.product.data.ProductCreateData
import com.example.gestok.screens.internalScreens.product.data.ProductData
import com.example.gestok.screens.internalScreens.product.data.ProductEditData
import com.example.gestok.screens.internalScreens.product.data.ProductStepData
import com.example.gestok.screens.internalScreens.product.data.ProductStepEditData
import com.example.gestok.screens.login.data.UserSession
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.awaitResponse
import java.io.File

class ProductApiViewModel(
    private val api: ProductService,
    private val cloudinary: CloudinaryService,
    private val lingva: LingvaService,
    private val spoonacular: SpoonacularService,
    override val sessaoUsuario: UserSession) :
    ProductViewModel(sessaoUsuario) {

    override fun getProdutos() {
        _produtosErro = null

        viewModelScope.launch {
            try {

                delay(1000)

                val resposta = api.getProdutos(sessaoUsuario.idEmpresa)

                _produtos.clear()
                if (resposta.isNotEmpty()) {
                    _produtos.addAll(resposta.map {
                        ProductData(
                            id = it.id,
                            nome = it.nome,
                            categoria = it.categoria,
                            preco = it.preco,
                            quantidade = it.quantidade,
                            imagem = it.imagem,
                            emProducao = it.emProducao
                        )
                    })
                }
                _carregouProdutos = true
                Log.d("API", "Quantidade de produtos encontrados: ${produtos.size}")

            } catch (e: HttpException) {

                if (e.code() == 500) _carregouProdutos = true

                Log.w("API", "Nem um produto encontrado: ${e.message} ")

            } catch (e: Exception) {
                _produtosErro = "Erro ao obter dados"
                Log.e("API", "Erro ao obter dados: ${e.message}")
            }
        }

    }

    override fun getCategorias() {
        _categoriasErro = null

        viewModelScope.launch {
            try {

                delay(1000)

                val resposta = api.getCategorias()

                _categorias.clear()
                if (resposta.isNotEmpty()) {
                    _categorias.addAll(resposta.map {
                        CategoryData(
                            id = it.id,
                            nome = it.nome,
                            subCategoria = it.subCategoria
                        )
                    })
                }

                Log.d("API", "Quantidade de categorias encontradas: ${categorias.size}")

            } catch (e: HttpException) {

                if (e.code() == 500) {}

                Log.w("API", "Nem uma categoria encontrada: ${e.message} ")

            } catch (e: Exception) {
                _categoriasErro = "Erro ao obter categorias"
                Log.e("API", "Erro ao obter dados: ${e.message}")
            }
        }
    }

    override fun deletarProduto(idProduto: Int, onBack: () -> Unit) {
        viewModelScope.launch {
            try {
                api.delete(idProduto)

                Log.d("API", "Produto excluído com sucesso")

                getProdutos()

                delay(2000)
                onBack()

            } catch (e: HttpException) {
                if (e.code() == 500){}
                Log.e("API", "Erro ao excluir produto: ${e.message}")

            } catch (e: Exception) {
                Log.e("API", "Erro ao conectar ao servidor: ${e.message}")
            }
        }
    }

    override fun getIngredientes() {
        _ingredientesErro = null

        viewModelScope.launch {
            try {

                delay(1000)

                val resposta = api.getIngredientes()

                _ingredientes.clear()
                if (resposta.isNotEmpty()) {
                    _ingredientes.addAll(resposta.map {
                        IngredientsData(
                            id = it.id,
                            nome = it.nome,
                            medida = it.medida,
                            quantidade = it.quantidade
                        )
                    })
                }
                _carregouIngredientes = true
                Log.d("API", "Quantidade de ingredientes encontrados: ${_ingredientes.size}")

            } catch (e: HttpException) {

                if (e.code() == 500) _carregouIngredientes = true

                Log.w("API", "Nem um ingrediente encontrado: ${e.message}")

            } catch (e: Exception) {
                _ingredientesErro = "Erro ao obter dados"
                Log.e("API", "Erro ao obter dados: ${e.message}")
            }
        }

    }

    override suspend fun uploadImagem(file: File): String? {
        return try {
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

            val uploadPresetBody = "ml_default".toRequestBody("text/plain".toMediaTypeOrNull())

            val response = withContext(Dispatchers.IO) {
                cloudinary.uploadImage(body, uploadPresetBody).awaitResponse()
            }

            if (response.isSuccessful) {
                response.body()?.public_id.also {
                    Log.d("Cloudinary", "Upload bem-sucedido: $it")
                }
            } else {
                Log.e("Cloudinary", "Erro no upload: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("Cloudinary", "Erro ao enviar imagem: ${e.message}")
            null
        }
    }

    override fun getImagem(publicId: String?): String? {
        if (publicId.isNullOrBlank()) {
            Log.e("Cloudinary", "Imagem não encontrada: $publicId")
            return null
        }

        Log.d("Cloudinary", "Imagem obtida com sucesso: $publicId")

        val cloudName = "ddmjnxjm7"
        return "https://res.cloudinary.com/$cloudName/image/upload/$publicId"
    }

    private suspend fun traduzirIngredientes(ingredientes: List<Ingrediente>): List<IngredienteTraduzido> = coroutineScope {

        ingredientes.mapNotNull { ing ->
            try {
                Log.e("TRADUÇÃO", "Traduzindo ingrediente: ${ing.nome}")
                val response = lingva.traduzirPtParaEn(ing.nome)
                IngredienteTraduzido(response.translation, ing)
            } catch (e: Exception) {
                Log.e("TRADUÇÃO", "Erro ao traduzir ingrediente ${ing.nome}: ${e.message}")
                null
            }
        }
    }

    private suspend fun buscarIdsSpoonacular(ingredientesTraduzidos: List<IngredienteTraduzido>): List<IngredienteComIdSpoonacular> = coroutineScope {

        ingredientesTraduzidos.mapNotNull { (nomeTraduzido, ingOriginal) ->
            try {
                Log.e("SPOONACULAR", "Buscando IDSPOONACULAR : ${nomeTraduzido}")
                val response = spoonacular.searchIngrediente(
                    query = nomeTraduzido,
                    apiKey = "51fc07ec2bd1411a99bd84cf92dc1646"
                )

                if (response.results.isNotEmpty()) {
                    val idSpoonacular = response.results.first().id
                    IngredienteComIdSpoonacular(
                        idOriginal = ingOriginal.id,
                        idSpoonacular = idSpoonacular,
                        medida = ingOriginal.medida,
                        quantidade = ingOriginal.quantidade
                    )
                } else null
            } catch (e: Exception) {
                Log.e("SPOONACULAR", "Erro ao buscar ingrediente $nomeTraduzido: ${e.message}")
                null
            }
        }
    }

    private suspend fun traduzirNutriente(nome: String): String {
        delay(2000)

        return try {
            Log.e("TRADUÇÃO", "Traduzindo nutrientes para PT: ${nome}")
            val response = lingva.traduzirEnParaPt(nome)
            response.translation
        } catch (e: Exception) {
            Log.e("TRADUÇÃO", "Erro ao traduzir nutriente $nome: ${e.message}")
            nome
        }
    }

    private suspend fun buscarInformacoesNutricionais(ingredientes: List<IngredienteComIdSpoonacular>): List<IngredienteComNutrientes> = coroutineScope {

        ingredientes.mapNotNull { ing ->
            try {
                Log.e("NUTRIENTES", "Buscando informações do IDSPOONACULAR: ${ing.idSpoonacular}")
                val response = spoonacular.getInformacaoIngrediente(
                    id = ing.idSpoonacular,
                    amount = ing.quantidade.toString(),
                    unit = ing.medida,
                    apiKey = "51fc07ec2bd1411a99bd84cf92dc1646"
                )

                val nutrientes = response.nutrition.nutrients.map {
                    Nutriente(
                        name = traduzirNutriente(it.name),
                        amount = it.amount,
                        unit = it.unit
                    )
                }.filter { it.amount > 0 }

                IngredienteComNutrientes(ing.idOriginal, nutrientes)
            } catch (e: Exception) {
                Log.e("NUTRIENTES", "Erro ao buscar nutrientes do ingrediente ID ${ing.idSpoonacular}: ${e.message}")
                null
            }
        }
    }

    private suspend fun enviarNutrientes(nutrientesPorIngrediente: List<IngredienteComNutrientes>) {
        nutrientesPorIngrediente.forEach { ingrediente ->
            ingrediente.nutrientes.forEach { nutriente ->
                try {
                    val payload = NutrientesBody(
                        tipo = nutriente.name,
                        pcComposicao = nutriente.amount.toString()
                    )
                    withContext(Dispatchers.IO) {
                        api.postNutrientes(
                            idIngrediente = ingrediente.idOriginal,
                            nutriente = payload
                        )
                    }
                    Log.d("NUTRIENTE", "Enviado nutriente ${nutriente.name} para ingrediente ${ingrediente.idOriginal}")
                } catch (e: Exception) {
                    Log.e("ENVIAR", "Erro ao enviar nutriente ${nutriente.name}: ${e.message}")
                }
            }
        }
    }

    private suspend fun processarIngredientes(ingredientes: List<Ingrediente>) {
        try {
            val traduzidos = traduzirIngredientes(ingredientes)
            val ids = buscarIdsSpoonacular(traduzidos)
            val nutrientes = buscarInformacoesNutricionais(ids)
            enviarNutrientes(nutrientes)
        } catch (e: Exception) {
            Log.e("PROCESSO", "Erro ao processar ingredientes: ${e.message}")
        }
    }

    private val mutex = Mutex()

    override fun salvarProduto(
        produto: ProductStepData,
        onBack: () -> Unit,
        onSucess: () -> Unit
    ) {

        limparErrosFormulario()

        var houveErro = false

        if (produto.nome.isBlank()) {
            _nomeErro = "Nome do produto é obrigatório"
            houveErro = true
        } else if (produto.nome.length < 2) {
            _nomeErro = "Nome do produto deve ter pelo menos 2 caracteres"
            houveErro = true
        } else if (produtos.any { it.nome.equals(produto.nome, ignoreCase = true) }) {
            _nomeErro = "Já existe um produto com este nome"
            houveErro = true
        }

        if (produto.preco == 0.0) {
            _precoErro = "Preço do produto é obrigatório"
            houveErro = true
        }

        if (produto.quantidade == 0) {
            _estoqueErro = "Quantidade em estoque é obrigatória"
            houveErro = true
        }

        if (produto.categoria == 0) {
            _categoriaErro = "Categoria do produto é obrigatória"
            houveErro = true
        }

        if (produto.subCategoria == 0) {
            _subCategoriaErro = "Sub Categoria do produto é obrigatória"
            houveErro = true
        }

        if (produto.ingredientes.any { it.quantidade == 0.0 }) {
            _itensErro = "Informe a quantidade dos ingredientes selecionados"
            houveErro = true
        }

        if (houveErro) return

        viewModelScope.launch {
            try {

                val produtoFormatado = ProductCreateData(
                    nome = produto.nome,
                    categoria = produto.subCategoria,
                    preco = produto.preco,
                    quantidade = produto.quantidade,
                    imagem = produto.imagem,
                    emProducao = produto.emProducao,
                    ingredientes = produto.ingredientes.map {
                        IngredientsProduct(
                            id = it.id,
                            nome = it.nome,
                            quantidade = it.quantidade
                        )
                    }
                )

                api.post(produtoFormatado, sessaoUsuario.idEmpresa)

                Log.d("API", "Produto cadastrado com sucesso")


                val ingredientesCopia = produto.ingredientes.map { it.copy() }


                val ingredientesFiltrados = ingredientesCopia.filter { ingrediente ->
                    val response = try {
                        api.getNutrientes(ingrediente.id)
                    } catch (e: Exception) {
                        Log.e("VALIDACAO", "Erro ao verificar nutrientes do ingrediente ${ingrediente.id}", e)
                        null
                    }

                    val possuiNutrientes = response?.isSuccessful == true && response.body()?.string()?.isNotBlank() == true

                    if (possuiNutrientes) {
                        Log.d("VALIDACAO", "Ingrediente ${ingrediente.id} já possui nutrientes vinculados")
                    }

                    !possuiNutrientes
                }

                launch {
                    mutex.withLock {
                        if (ingredientesFiltrados.isNotEmpty()) {
                            Log.d("PROCESSO", "Iniciando processamento de ingredientes em segundo plano")
                            processarIngredientes(ingredientesFiltrados)
                        } else {
                            Log.d("PROCESSO", "Nenhum ingrediente novo para processar")
                        }
                    }
                }

                onSucess()
                getProdutos()

                delay(1500)
                onBack()


            } catch (e: HttpException) {
                if (e.code() == 400) {}
                Log.e("API", "Erro ao cadastrar produto: ${e.message}")
                _cadastroErro = "Erro ao cadastrar produto"

            } catch (e: Exception) {
                Log.e("API", "Erro ao conectar ao servidor: ${e.message}")
                _cadastroErro = "Erro ao conectar ao servidor"
            }
        }
    }

    val isUpdatingMap = mutableStateMapOf<Int, Boolean>()

    override fun atualizarProducao(produto: ProductData) {
        viewModelScope.launch {
            try {
                isUpdatingMap[produto.id] = true
                val produtoAtualizado = ProductEditData(
                    nome = produto.nome,
                    categoria = produto.categoria,
                    preco = produto.preco,
                    quantidade = produto.quantidade,
                    imagem = produto.imagem,
                    emProducao = !produto.emProducao
                )

                api.put(produto.id, produtoAtualizado,)

                Log.d("API", "Produto atualizado com sucesso")

                delay(500)
                isUpdatingMap[produto.id] = false
                getProdutos()

            } catch (e: HttpException) {
                if (e.code() == 400) {}
                Log.e("API", "Erro ao atualizar produto: ${e.message}")

            } catch (e: Exception) {
                Log.e("API", "Erro ao conectar ao servidor: ${e.message}")
            }
        }
    }

    override fun atualizarEstoque(produtos: List<ProductData>, onBack: () -> Unit, onSucess: () -> Unit) {
        var houveErro = false

        if (produtos.any { it.quantidade == 0 }) {
            _itensErro = "Informe a quantidade dos produtos selecionados"
            houveErro = true
        }

        if (houveErro) return

        viewModelScope.launch {
            try {
                for (produto in produtos) {
                    val produtoAtualizado = ProductEditData(
                        nome = produto.nome,
                        categoria = produto.categoria,
                        preco = produto.preco,
                        quantidade = produto.quantidade,
                        imagem = produto.imagem,
                        emProducao = produto.emProducao
                    )

                    api.put(produto.id, produtoAtualizado)
                    Log.d("API", "Produto ${produto.nome} atualizado com sucesso")
                }
                onSucess()
                getProdutos()

                delay(1500)
                onBack()
            } catch (e: HttpException) {
                if (e.code() == 400) {}
                Log.e("API", "Erro ao atualizar estoque: ${e.message}")
            } catch (e: Exception) {
                Log.e("API", "Erro ao conectar ao servidor: ${e.message}")
            }
        }
    }

    override fun getReceita(produto: ProductData, onResult: (List<IngredientsRecipe>) -> Unit) {
        viewModelScope.launch {
            try {
                delay(1000)

                val receitas: List<RecipeData> = api.getReceitas()
                val ingredientes: List<IngredientsData> = api.getIngredientes()

                val receitasDoProduto = receitas.filter { it.produto == produto.id }

                val ingredientesReceita = receitasDoProduto.mapNotNull { receita ->
                    val ingrediente = ingredientes.find { it.id == receita.ingrediente }

                    ingrediente?.let {
                        IngredientsRecipe(
                            id = receita.idReceita,
                            idIngrediente = it.id,
                            nome = it.nome,
                            medida = it.medida,
                            quantidade = receita.quantidade
                        )
                    }
                }

                Log.d("API", "Ingredientes da receita obtidos com sucesso")
                onResult(ingredientesReceita)
                _carregouReceita = true

            } catch (e: Exception) {
                Log.e("API", "Erro ao obter ingredientes: ${e.message}")
                onResult(emptyList())
            }
        }
    }

    override fun editarProduto(
        produto: ProductStepEditData,
        onBack: () -> Unit,
        onSucess: () -> Unit
    ) {
        limparErrosFormulario()

        var houveErro = false

        if (produto.nome.isBlank()) {
            _nomeErro = "Nome do produto é obrigatório"
            houveErro = true
        } else if (produto.nome.length < 2) {
            _nomeErro = "Nome do produto deve ter pelo menos 2 caracteres"
            houveErro = true
        } else if (produtos.any {
                it.nome.equals(produto.nome, ignoreCase = true) && it.id != produto.id
            }) {
            _nomeErro = "Já existe um produto com este nome"
            houveErro = true
        }

        if (produto.preco == 0.0) {
            _precoErro = "Preço do produto é obrigatório"
            houveErro = true
        }

        if (produto.quantidade == 0) {
            _estoqueErro = "Quantidade em estoque é obrigatória"
            houveErro = true
        }

        if (produto.ingredientes.any { it.quantidade == 0.0 }) {
            _itensErro = "Informe a quantidade dos ingredientes selecionados"
            houveErro = true
        }

        if (houveErro) return

        viewModelScope.launch {
            try {

                val produtoFormatado = ProductEditData(
                    nome = produto.nome,
                    categoria = produto.categoria,
                    preco = produto.preco,
                    quantidade = produto.quantidade,
                    imagem = produto.imagem,
                    emProducao = produto.emProducao,
                )

                api.put(produto.id, produtoFormatado)

                for (ingrediente in produto.ingredientes) {
                    val receita = RecipeBody(
                        quantidade = ingrediente.quantidade,
                        idIngrediente = ingrediente.idIngrediente,
                        idProduto = produto.id,
                    )

                    if(ingrediente.id == 0)
                        api.postReceita(receita)
                    else {
                        api.putReceita(ingrediente.id, receita)
                    }
                }

                Log.d("API", "Produto editado com sucesso")

                val ingredientesCopia = produto.ingredientes.map { it.copy() }

                val ingredientesFiltrados = ingredientesCopia.filter { ingrediente ->
                    val response = try {
                        api.getNutrientes(ingrediente.idIngrediente)
                    } catch (e: Exception) {
                        Log.e("VALIDACAO", "Erro ao verificar nutrientes do ingrediente ${ingrediente.idIngrediente}", e)
                        null
                    }

                    val possuiNutrientes = response?.isSuccessful == true && response.body()?.string()?.isNotBlank() == true

                    if (possuiNutrientes) {
                        Log.d("VALIDACAO", "Ingrediente ${ingrediente.idIngrediente} já possui nutrientes vinculados")
                    }

                    !possuiNutrientes
                }

                launch {
                    mutex.withLock {
                        if (ingredientesFiltrados.isNotEmpty()) {
                            Log.d("PROCESSO", "Iniciando processamento de ingredientes em segundo plano")
                            processarIngredientes(
                                ingredientesFiltrados.map{
                                    Ingrediente(
                                        id = it.idIngrediente,
                                        nome = it.nome,
                                        medida = it.medida.toString(),
                                        quantidade = it.quantidade
                                    )
                                }
                            )
                        } else {
                            Log.d("PROCESSO", "Nenhum ingrediente novo para processar")
                        }
                    }
                }

                onSucess()
                getProdutos()

                delay(1500)
                onBack()

            } catch (e: HttpException) {
                if (e.code() == 400) {}
                Log.e("API", "Erro ao editar produto: ${e.message}")
                _edicaoErro = "Erro ao editar produto"

            } catch (e: Exception) {
                Log.e("API", "Erro ao conectar ao servidor: ${e.message}")
                _edicaoErro = "Erro ao conectar ao servidor"
            }
        }
    }

    override fun deletarReceita(idReceita: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                api.deleteReceita(idReceita)

                Log.d("API", "Receita excluída com sucesso")

                onResult(true)

            } catch (e: HttpException) {
                if (e.code() == 500) {}
                Log.e("API", "Erro ao excluir receita: ${e.message}")
                onResult(false)

            } catch (e: Exception) {
                Log.e("API", "Erro ao conectar ao servidor: ${e.message}")
            }
        }
    }

    override fun salvarIngrediente(idProduto: Int, ingrediente: IngredientsBody,  onBack: () -> Unit) {
        limparErrosFormularioIngrediente()

        var houveErro = false

        if (ingrediente.nome.isBlank()) {
            _nomeIngredienteErro = "Nome do ingrediente é obrigatório"
            houveErro = true
        } else if (ingrediente.nome.length < 2) {
            _nomeIngredienteErro = "Nome do ingrediente deve ter pelo menos 2 caracteres"
            houveErro = true
        }

        if (ingrediente.quantidade == 0) {
            _quantidadeIngredienteErro = "Quantidade do ingrediente é obrigatória"
            houveErro = true
        }

        if (ingrediente.medida == 0.0) {
            _medidaIngredienteErro = "Medida do ingrediente é obrigatória"
            houveErro = true
        }

        if (houveErro) return

        viewModelScope.launch {
            try {

                api.postIngrediente(idProduto, ingrediente)

                Log.d("API", "Ingrediente cadastrado com sucesso")

                getIngredientes()
                onBack()

            } catch (e: HttpException) {
                if (e.code() == 400) {}
                Log.e("API", "Erro ao cadastrar ingrediente: ${e.message}")
                _cadastroIngredienteErro = "Erro ao cadastrar ingrediente"

            } catch (e: Exception) {
                Log.e("API", "Erro ao conectar ao servidor: ${e.message}")
                _cadastroIngredienteErro = "Erro ao conectar ao servidor"
            }
        }
    }

    override fun editarIngrediente(
        idIngrediente: Int,
        ingrediente: IngredientsBody,
        onBack: () -> Unit
    ) {
        limparErrosFormularioIngrediente()

        var houveErro = false

        if (ingrediente.nome.isBlank()) {
            _nomeIngredienteErro = "Nome do ingrediente é obrigatório"
            houveErro = true
        } else if (ingrediente.nome.length < 2) {
            _nomeIngredienteErro = "Nome do ingrediente deve ter pelo menos 2 caracteres"
            houveErro = true
        }

        if (ingrediente.quantidade == 0) {
            _quantidadeIngredienteErro = "Quantidade do ingrediente é obrigatória"
            houveErro = true
        }

        if (ingrediente.medida == 0.0) {
            _medidaIngredienteErro = "Medida do ingrediente é obrigatória"
            houveErro = true
        }

        if (houveErro) return

        viewModelScope.launch {
            try {

                api.putIngrediente(ingrediente, idIngrediente)

                Log.d("API", "Ingrediente editado com sucesso")

                getIngredientes()
                onBack()

            } catch (e: HttpException) {
                if (e.code() == 500) {}
                Log.e("API", "Erro ao editar ingrediente: ${e.message}")
                _edicaoIngredienteErro = "Erro ao editar ingrediente"

            } catch (e: Exception) {
                Log.e("API", "Erro ao conectar ao servidor: ${e.message}")
                _edicaoIngredienteErro = "Erro ao conectar ao servidor"
            }
        }
    }

    override fun deletarIngrediente(idIngrediente: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                api.deleteIngrediente(idIngrediente)

                Log.d("API", "Ingrediente excluído com sucesso")

                onResult(true)

            } catch (e: HttpException) {
                if (e.code() == 500) {}
                Log.e("API", "Erro ao excluir ingrediente: ${e.message}")
                onResult(false)

            } catch (e: Exception) {
                Log.e("API", "Erro ao conectar ao servidor: ${e.message}")
            }
        }
    }

    override fun getNutrientes(ingredientes: List<IngredientsRecipe>, onFinished: () -> Unit) {
        _nutrientes.clear()

        viewModelScope.launch {
            try {
                val gson = Gson()

                for (ingrediente in ingredientes) {
                    val resposta: Response<ResponseBody> = api.getNutrientes(ingrediente.idIngrediente)

                    if (resposta.isSuccessful) {
                        val bodyString = resposta.body()?.string()

                        if (!bodyString.isNullOrBlank()) {
                            val tipo = object : TypeToken<List<NutrientesResponse>>() {}.type
                            val lista: List<NutrientesResponse> = gson.fromJson(bodyString, tipo)

                            if (lista.isNotEmpty()) {
                                _nutrientes.addAll(lista)
                            }
                        } else {
                            Log.w("API", "Corpo vazio para ${ingrediente.idIngrediente}")
                        }
                    } else {
                        Log.w("API", "Erro na resposta para ${ingrediente.idIngrediente}")
                    }
                }

            } catch (e: Exception) {
                Log.e("API", "Erro ao obter nutrientes: ${e.message}")
            } finally {
                onFinished()
            }
        }
    }

}