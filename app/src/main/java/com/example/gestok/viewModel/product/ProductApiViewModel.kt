package com.example.gestok.viewModel.product

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.viewModelScope
import com.example.gestok.network.service.CloudinaryService
import com.example.gestok.network.service.ProductService
import com.example.gestok.screens.internalScreens.product.data.CategoryData
import com.example.gestok.screens.internalScreens.product.data.IngredientsData
import com.example.gestok.screens.internalScreens.product.data.ProductCreateData
import com.example.gestok.screens.internalScreens.product.data.ProductData
import com.example.gestok.screens.internalScreens.product.data.ProductEditData
import com.example.gestok.screens.internalScreens.product.data.ProductStepData
import com.example.gestok.screens.login.data.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.io.File

class ProductApiViewModel(private val api: ProductService, private val cloudinary: CloudinaryService, override val sessaoUsuario: UserSession) :
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

            val response = withContext(Dispatchers.IO) {
                cloudinary.uploadImage(body, "ml_default").awaitResponse()
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

    override fun salvarProoduto(
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
                    ingredientes = produto.ingredientes
                )

                api.post(produtoFormatado, sessaoUsuario.idEmpresa)

                Log.d("API", "Produto cadastrado com sucesso")

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
}