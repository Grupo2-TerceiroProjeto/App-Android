package com.example.gestok.components

import BottomNavBar
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.gestok.components.adminpage.AdminContent
import com.example.gestok.components.adminpage.RegisterData
import com.example.gestok.components.orderpage.OrderContent
import com.example.gestok.components.orderpage.OrderData
import com.example.gestok.components.productpage.IngredientData
import com.example.gestok.components.productpage.ProductContent
import com.example.gestok.components.productpage.ProductData
import com.example.gestok.screens.internalScreens.dashboard.Dashboard
import com.example.gestok.screens.internalScreens.Profile
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



//// Pedidos mockados
//val pedido1: OrderData
//    get() = OrderData(
//        nomeSolicitante = "Vitor Nunes",
//        contato = "1140028922",
//        statusPedido = "Em produção",
//        dataEntrega = "20/11/2023",
//        itens = mutableMapOf(coxinha to 20, esfirra to 30, brigadeiro to 100) // Pedido com alguns salgados e um doce
//    )
//
//val pedido2: OrderData
//    get() = OrderData(
//        nomeSolicitante = "Emilly Ferreira",
//        contato = "1198765432",
//        statusPedido = "Aguardando entrega",
//        dataEntrega = "22/11/2023",
//        itens = mutableMapOf(pastel to 10, boloDeChocolate to 2) // Outro pedido com um salgado e um bolo
//    )
//
//val pedido3: OrderData
//    get() = OrderData(
//        nomeSolicitante = "Thiago Rodrigues",
//        contato = "1122334455",
//        statusPedido = "Entregue",
//        dataEntrega = "15/11/2023",
//        itens = mutableMapOf(coxinha to 60, esfirra to 50, brigadeiro to 10) // Pedido com mais itens e repetidos
//    )



//Funcionário testes:
val luca = RegisterData("Luca Sena", "Cozinheiro", "luca.souza@sptech.com")
val emilly = RegisterData("Emilly Ferreira", "Atendente", "emilly.ferreira@sptech.com")
val vitor = RegisterData("Vitor Hugo", "Gerente", "vitor.hugo@sptech.com")
val thiago = RegisterData("Thiago Rodrigues", "Auxiliar de Cozinha", "thiago.rodrigues@sptech.com")
val vagner = RegisterData("Vagner Benedito", "Chef de Cozinha", "vagner.benedito@sptech.com")
val kauan = RegisterData("Kauan Parente", "Estoquista", "kauan.parente@sptech.com")

//Produtos testes:
val coxinha = ProductData("Coxinha", 10, "Salgados", 100.10, mutableListOf(margarina, farinha, frango, ovo), true)
val esfirra = ProductData("Esfirra", 15, "Salgados", 80.50, mutableListOf(farinha, margarina, queijo, presunto), true)
val pastel = ProductData("Pastel", 20, "Salgados", 90.00, mutableListOf(farinha, ovo, queijo, presunto, oleo), true)
val brigadeiro = ProductData("Brigadeiro", 30, "Doces", 60.75, mutableListOf(chocolate, leite, acucar, margarina), true)
val boloDeChocolate = ProductData("Bolo de Chocolate", 5, "Doces", 120.00, mutableListOf(farinha, leite, chocolate, ovo, fermento, acucar), true)

val listaProdutos: List<ProductData> = listOf(coxinha, esfirra, pastel, brigadeiro, boloDeChocolate)
val listaFuncionarios: List<RegisterData> = listOf(luca, emilly, vitor, thiago, vagner, kauan)
//val listaPedidos: List<OrderData> = listOf(pedido1, pedido2, pedido3)

@Composable
fun LayoutScreen(
    activity: Activity

) {

    val currentPage = remember { mutableStateOf("dashboard") }

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
                        .padding(innerPadding), viewModel
                )

            }

            "config" -> {
                AdminContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding), listaFuncionarios
                )
            }

            "produtos" -> {
                ProductContent(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding), listaProdutos)
            }
        }


    }
}
