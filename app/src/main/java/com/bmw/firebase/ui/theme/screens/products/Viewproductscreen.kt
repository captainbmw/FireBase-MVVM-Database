package com.bmw.firebase.ui.theme.screens.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bmw.firebase.data.productviewmodel
import com.bmw.firebase.model.Product
import com.bmw.firebase.navigation.ROUTE_UPDATE_PRODUCT


@Composable
fun ViewProductsScreen(navController:NavHostController) {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        var context = LocalContext.current
        var productRepository = productviewmodel(navController, context)
        val emptyProductState = remember { mutableStateOf(Product("","","","")) }
        var emptyProductsListState = remember { mutableStateListOf<Product>() }

        var products = productRepository.viewProducts(emptyProductState, emptyProductsListState)


        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "All products",
                fontSize = 45.sp,
                fontFamily = FontFamily.Cursive,
                color = Color.Blue)

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                items(products){
                    ProductItem(
                        name = it.name,
                        quantity = it.quantity,
                        price = it.price,
                        id = it.id,
                        navController = navController,
                        productRepository = productRepository
                    )
                }
            }
        }
    }

}

@Composable
fun ProductItem(name:String, quantity:String, price:String, id:String,
                navController:NavHostController, productRepository: productviewmodel
) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.Cyan),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = name)
        Text(text = quantity)
        Text(text = price)
        Button(onClick = {
            productRepository.deleteProduct(id)
        },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(text = "Delete",
                color = Color.Blue)
        }
        Button(onClick = {
            navController.navigate(ROUTE_UPDATE_PRODUCT+"/$id")
        },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
            Text(text = "Update",
                color = Color.Cyan)
        }
    }

}

@Preview
@Composable
fun view() {
    ViewProductsScreen(rememberNavController())

}