package com.example.fitgo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apolloproject.ui.common.BottomBar
import com.example.apolloproject.ui.screens.logincompose.pantallaLogin
import com.example.apolloproject.ui.screens.splashscreen.SplashScreen
import com.example.fitgo.R
import com.example.fitgo.ui.entrenamientoscompose.EntrenamientosLista

@Composable

fun navigationAct() {
    val navController = rememberNavController()
    val splashRoute= stringResource(id = R.string.splash)
    val pantallaLogin=   stringResource(id = R.string.pantallaLogin)
    val entrenamientosList= stringResource(id =R.string.entrenamientosPath )
    NavHost(
        navController = navController,
        startDestination = splashRoute,

        ){
        composable(
            splashRoute
        ){
            SplashScreen(navController = navController)
        }
        composable(pantallaLogin) {
            pantallaLogin(navController)
        }
        composable(
                 entrenamientosList
        ){
            EntrenamientosLista(
                navController,
                onViewDetalle = {customerId ->
                    navController.navigate("detalleCust/${customerId}")
                },
                bottomNavigationBar =  {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar)
                }
            )

        }
    }
}