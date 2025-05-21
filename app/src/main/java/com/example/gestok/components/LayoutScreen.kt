package com.example.gestok.components

import BottomNavBar
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.gestok.screens.internalScreens.order.OrderCreate
import com.example.gestok.screens.internalScreens.order.OrderEdit
import com.example.gestok.screens.internalScreens.order.OrderContent
import com.example.gestok.components.productpage.IngredientData
import com.example.gestok.screens.internalScreens.product.ProductContent
import com.example.gestok.components.productpage.ProductData
import com.example.gestok.screens.internalScreens.dashboard.Dashboard
import com.example.gestok.screens.internalScreens.Profile
import com.example.gestok.screens.internalScreens.admin.AdminContent
import com.example.gestok.screens.internalScreens.admin.RegisterCreate
import com.example.gestok.screens.internalScreens.admin.RegisterEdit
import com.example.gestok.screens.internalScreens.admin.data.RegisterData
import com.example.gestok.screens.internalScreens.order.data.OrderData
import com.example.gestok.viewModel.admin.AdminApiViewModel
import com.example.gestok.viewModel.dashboard.DashboardApiViewModel
import com.example.gestok.viewModel.order.OrderApiViewModel
import org.koin.androidx.compose.koinViewModel


//Ingredientes testes:
val farinha: IngredientData = IngredientData("Farinha", 1, "kg")
val margarina: IngredientData = IngredientData("Margarina", 500, "g")
val leite: IngredientData = IngredientData("Leite", 1, "L")
val ovo: IngredientData = IngredientData("Ovo", 12, "unidades")
val fermento: IngredientData = IngredientData("Fermento", 10, "g")
val chocolate: IngredientData = IngredientData("Chocolate", 200, "g")
val frango: IngredientData = IngredientData("Frango", 500, "g")
val queijo: IngredientData = IngredientData("Queijo", 300, "g")
val presunto: IngredientData = IngredientData("Presunto", 200, "g")
val oleo: IngredientData = IngredientData("Óleo", 1, "L")
val acucar: IngredientData = IngredientData("Açúcar", 300, "g")


//Produtos testes:
val coxinha = ProductData("Coxinha", 10, "Salgados", 100.10, mutableListOf(margarina, farinha, frango, ovo), true)
val esfirra = ProductData("Esfirra", 15, "Salgados", 80.50, mutableListOf(farinha, margarina, queijo, presunto), true)
val pastel = ProductData("Pastel", 20, "Salgados", 90.00, mutableListOf(farinha, ovo, queijo, presunto, oleo), true)
val brigadeiro = ProductData("Brigadeiro", 30, "Doces", 60.75, mutableListOf(chocolate, leite, acucar, margarina), true)
val boloDeChocolate = ProductData("Bolo de Chocolate", 5, "Doces", 120.00, mutableListOf(farinha, leite, chocolate, ovo, fermento, acucar), true)
val pudim = ProductData("Pudim", 25, "Doces", 70.50, mutableListOf(chocolate, leite, acucar, margarina), true)
val quibe = ProductData("Quibe", 18, "Salgados", 85.00, mutableListOf(chocolate, leite, acucar, margarina), true)
val mousseDeMaracuja = ProductData("Mousse de Maracujá", 8, "Doces", 65.00, mutableListOf(chocolate, leite, acucar, margarina), true)
val tortaDeFrango = ProductData("Torta de Frango", 12, "Salgados", 110.00, mutableListOf(chocolate, leite, acucar, margarina), true)

val listaProdutos: List<ProductData> = listOf(
    coxinha, esfirra, pastel, brigadeiro, boloDeChocolate,
    pudim, quibe, mousseDeMaracuja, tortaDeFrango
)

@Composable
fun LayoutScreen(
    activity: Activity

) {

    val currentPage = remember { mutableStateOf("dashboard") }
    val previousPage = remember { mutableStateOf("") }

    val selectedOrder = remember { mutableStateOf<OrderData?>(null) }
    val selectedRegister = remember { mutableStateOf<RegisterData?>(null) }

    LaunchedEffect(currentPage.value) {
        if (currentPage.value != "sucess") {
            previousPage.value = currentPage.value
        }
    }

    Scaffold(Modifier.background(Color(0xFFF3F3F3)), //COR DO FUNDO DA TELA
        topBar = {
            Topbar(activity)
        },
        bottomBar = {
            BottomNavBar() { navItem ->
                currentPage.value = navItem.screen
            }
        }
    ) { innerPadding ->
        when (currentPage.value) {

            "dashboard" -> {
                val viewModel: DashboardApiViewModel = koinViewModel()

                Dashboard(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding), viewModel
                )

            }

            "perfil" -> {
                Profile(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                )

            }

            "pedidos" -> {
                val viewModel: OrderApiViewModel = koinViewModel()

                OrderContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    viewModel,
                    currentPage = currentPage,
                    selectedOrder = selectedOrder
                )

            }

            "createOrder" -> {
                val viewModel: OrderApiViewModel = koinViewModel()

                OrderCreate(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    onBack = {
                        currentPage.value = "pedidos"
                    },
                    onSucess = {
                        currentPage.value = "sucess"
                    },
                    viewModel

                )
            }

            "editOrder" -> {
                val viewModel: OrderApiViewModel = koinViewModel()

                selectedOrder.value?.let {
                    OrderEdit(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        onBack = {
                            currentPage.value = "pedidos"
                        },
                        order = it,
                        onSucess = {
                            currentPage.value = "sucess"
                        },
                        viewModel
                    )
                }
            }

            "config" -> {
                val viewModel: AdminApiViewModel = koinViewModel()

                AdminContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    viewModel,
                    currentPage = currentPage,
                    selectedRegister = selectedRegister
                )
            }

            "createRegister" -> {
                val viewModel: AdminApiViewModel = koinViewModel()

                RegisterCreate(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    onBack = {
                        currentPage.value = "config"
                    },
                    onSucess = {
                        currentPage.value = "sucess"
                    },
                    viewModel
                )
            }

            "editRegister" -> {
                val viewModel: AdminApiViewModel = koinViewModel()

                selectedRegister.value?.let {
                    RegisterEdit(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        onBack = {
                            currentPage.value = "config"
                        },
                        funcionario = it,
                        onSucess = {
                            currentPage.value = "sucess"
                        },
                        viewModel
                    )
                }
            }

            "produtos" -> {
                ProductContent(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding), listaProdutos)
            }

            "sucess" -> {

                val (title, message) = when (previousPage.value) {
                    "createOrder" -> Pair(
                        "Pedido Cadastrado",
                        "O pedido foi cadastrado com sucesso! \nConfira na aba Pedidos"
                    )

                    "editOrder" -> Pair(
                        "Pedido Atualizado",
                        "O pedido foi atualizado com sucesso! \nConfira na aba Pedidos"
                    )

                    "createRegister" -> Pair(
                        "Funcionário Cadastrado",
                        "O funcionario foi cadastrado com sucesso! \nConfira na aba Administração"
                    )

                    "editRegister" -> Pair(
                        "Funcionário Atualizado",
                        "O funcionário foi atualizado com sucesso! \nConfira na aba Administração"
                    )

                    else -> Pair(
                        "",
                        ""
                    )
                }

                Sucess(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    title = title,
                    message = message
                )
            }
        }


    }
}
