package com.example.gestok.viewModel.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gestok.screens.internalScreens.product.data.ProductData
import com.example.gestok.screens.login.data.UserSession
import com.example.gestok.screens.internalScreens.product.data.CategoryData
import com.example.gestok.screens.internalScreens.product.data.IngredientsBody
import com.example.gestok.screens.internalScreens.product.data.IngredientsData
import com.example.gestok.screens.internalScreens.product.data.IngredientsRecipe
import com.example.gestok.screens.internalScreens.product.data.NutrientesResponse
import com.example.gestok.screens.internalScreens.product.data.ProductStepData
import com.example.gestok.screens.internalScreens.product.data.ProductStepEditData
import java.io.File

abstract class ProductViewModel(open val sessaoUsuario : UserSession) : ViewModel() {

    protected var _produtosErro by mutableStateOf<String?>(null)

    protected var _ingredientesErro by mutableStateOf<String?>(null)

    protected var _categoriasErro by mutableStateOf<String?>(null)

    protected var _produtos = mutableStateListOf<ProductData>()

    protected var _ingredientes = mutableStateListOf<IngredientsData>()

    protected var _nutrientes = mutableStateListOf<NutrientesResponse>()

    protected var _categorias = mutableStateListOf<CategoryData>()

    protected var _carregouProdutos by mutableStateOf(false)

    protected var _carregouIngredientes by mutableStateOf(false)

    protected var _carregouReceita by mutableStateOf(false)

    protected var _nomeErro by mutableStateOf<String?>(null)

    protected var _precoErro by mutableStateOf<String?>(null)

    protected var _estoqueErro by mutableStateOf<String?>(null)

    protected var _categoriaErro by mutableStateOf<String?>(null)

    protected var _subCategoriaErro by mutableStateOf<String?>(null)

    protected var _itensErro by mutableStateOf<String?>(null)

    protected var _cadastroErro by mutableStateOf<String?>(null)

    protected var _edicaoErro by mutableStateOf<String?>(null)

    protected var _nomeIngredienteErro by mutableStateOf<String?>(null)

    protected var _quantidadeIngredienteErro by mutableStateOf<String?>(null)

    protected var _medidaIngredienteErro by mutableStateOf<String?>(null)

    protected var _cadastroIngredienteErro by mutableStateOf<String?>(null)

    protected var _edicaoIngredienteErro by mutableStateOf<String?>(null)

    val produtosErro: String?
        get() = _produtosErro

    val produtos: List<ProductData>
        get() = _produtos.toList()

    val carregouProdutos: Boolean
        get() = _carregouProdutos

    val ingredientesErro: String?
        get() = _ingredientesErro

    val ingredientes: List<IngredientsData>
        get() = _ingredientes.toList()

    val carregouIngredientes: Boolean
        get() = _carregouIngredientes

    val carregouReceita: Boolean
        get() = _carregouReceita

    val categoriasErro: String?
        get() = _categoriasErro

    val categorias: List<CategoryData>
        get() = _categorias.toList()

    val nomeErro: String?
        get() = _nomeErro

    val precoErro: String?
        get() = _precoErro

    val estoqueErro: String?
        get() = _estoqueErro

    val categoriaErro: String?
        get() = _categoriaErro

    val subCategoriaErro: String?
        get() = _subCategoriaErro

    val itensErro: String?
        get() = _itensErro

    val cadastroErro: String?
        get() = _cadastroErro

    val edicaoErro: String?
        get() = _edicaoErro

    val nomeIngredienteErro: String?
        get() = _nomeIngredienteErro

    val quantidadeIngredienteErro: String?
        get() = _quantidadeIngredienteErro

    val medidaIngredienteErro: String?
        get() = _medidaIngredienteErro

    val cadastroIngredienteErro: String?
        get() = _cadastroIngredienteErro

    val edicaoIngredienteErro: String?
        get() = _edicaoIngredienteErro

    val nutrientes: List<NutrientesResponse>
        get() = _nutrientes.toList()

    fun limparErrosFormulario() {
        _nomeErro = null
        _precoErro = null
        _estoqueErro = null
        _categoriaErro = null
        _subCategoriaErro = null
        _itensErro = null
    }

    fun limparErrosFormularioIngrediente() {
        _nomeIngredienteErro = null
        _quantidadeIngredienteErro = null
        _medidaIngredienteErro = null
    }

    open fun getProdutos() {}

    open fun getCategorias() {}

    open fun deletarProduto(idProduto: Int, onBack: () -> Unit) {}

    open fun getIngredientes() {}

    open fun salvarProduto(produto : ProductStepData, onBack: () -> Unit, onSucess: () -> Unit) {}

    abstract suspend fun uploadImagem(file: File): String?

    abstract fun getImagem(publicId: String?): String?

    open fun atualizarProducao(produto : ProductData) {}

    open fun atualizarEstoque(produtos : List<ProductData>, onBack: () -> Unit, onSucess: () -> Unit) {}

    open fun getReceita(produto: ProductData, onResult: (List<IngredientsRecipe>) -> Unit) {}

    open fun editarProduto(produto : ProductStepEditData, onBack: () -> Unit, onSucess: () -> Unit) {}

    open fun deletarReceita(idReceita: Int, onResult: (Boolean) -> Unit) {}

    open fun salvarIngrediente(idProduto: Int, ingrediente : IngredientsBody,  onBack: () -> Unit) {}

    open fun editarIngrediente(idIngrediente: Int, ingrediente : IngredientsBody,  onBack: () -> Unit) {}

    open fun deletarIngrediente(idIngrediente: Int, onResult: (Boolean) -> Unit) {}

    fun removerIngredienteLocal(id: Int) {
        _ingredientes.removeIf { it.id == id }
    }

    open fun getNutrientes(ingredientes : List<IngredientsRecipe>, onFinished: () -> Unit) {}

}