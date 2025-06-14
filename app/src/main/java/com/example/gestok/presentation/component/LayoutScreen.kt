package com.example.gestok.presentation.component

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
import com.example.gestok.presentation.component.screens.internalScreens.order.OrderCreate
import com.example.gestok.presentation.component.screens.internalScreens.order.OrderEdit
import com.example.gestok.presentation.component.screens.internalScreens.order.OrderContent
import com.example.gestok.presentation.component.screens.internalScreens.product.ProductContent
import com.example.gestok.presentation.component.screens.internalScreens.dashboard.Dashboard
import com.example.gestok.presentation.component.screens.internalScreens.profile.Profile
import com.example.gestok.presentation.component.screens.internalScreens.admin.AdminContent
import com.example.gestok.presentation.component.screens.internalScreens.admin.RegisterCreate
import com.example.gestok.presentation.component.screens.internalScreens.admin.RegisterEdit
import com.example.gestok.domain.model.admin.RegisterModel
import com.example.gestok.domain.model.order.OrderModel
import com.example.gestok.presentation.component.screens.internalScreens.product.ProductCreate
import com.example.gestok.presentation.component.screens.internalScreens.product.ProductEdit
import com.example.gestok.presentation.component.screens.internalScreens.product.StockAdd
import com.example.gestok.domain.model.product.ProductModel
import com.example.gestok.presentation.viewmodel.admin.AdminApiViewModel
import com.example.gestok.presentation.viewmodel.dashboard.DashboardApiViewModel
import com.example.gestok.presentation.viewmodel.order.OrderApiViewModel
import com.example.gestok.presentation.viewmodel.product.ProductApiViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LayoutScreen(
    activity: Activity

) {

    val currentPage = remember { mutableStateOf("dashboard") }
    val previousPage = remember { mutableStateOf("") }

    val selectedOrder = remember { mutableStateOf<OrderModel?>(null) }
    val selectedRegister = remember { mutableStateOf<RegisterModel?>(null) }
    val selectedProduct = remember { mutableStateOf<ProductModel?>(null) }

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
                val viewModel: ProductApiViewModel = koinViewModel()

                ProductContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    viewModel,
                    currentPage = currentPage,
                    selectedProduct = selectedProduct
                )
            }

            "createProduct" -> {
                val viewModel: ProductApiViewModel = koinViewModel()

                ProductCreate(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    onBack = {
                        currentPage.value = "produtos"
                    },
                    onSucess = {
                        currentPage.value = "sucess"
                    },
                    viewModel

                )
            }

            "editProduct" -> {
                val viewModel: ProductApiViewModel = koinViewModel()

                selectedProduct.value?.let {
                    ProductEdit(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        onBack = {
                            currentPage.value = "produtos"
                        },
                        product = it,
                        onSucess = {
                            currentPage.value = "sucess"
                        },
                        viewModel
                    )
                }
            }

            "stockAdd" -> {
                val viewModel: ProductApiViewModel = koinViewModel()

                StockAdd(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    onBack = {
                        currentPage.value = "produtos"
                    },
                    onSucess = {
                        currentPage.value = "sucess"
                    },
                    viewModel

                )
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

                    "createProduct" -> Pair(
                        "Produto Cadastrado",
                        "O produto foi cadastrado com sucesso! \nConfira na aba Produtos"
                    )

                    "editProduct" -> Pair(
                        "Produto Atualizado",
                        "O produto foi atualizado com sucesso! \nConfira na aba Produtos"
                    )

                    "stockAdd" -> Pair(
                        "Estoque Atualizado",
                        "O estoque foi atualizado com sucesso! \nConfira na aba Produtos"
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

