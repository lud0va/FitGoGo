package com.example.fitgo.ui.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.apolloproject.ui.common.BottomBar
import com.example.apolloproject.ui.screens.logincompose.pantallaLogin
import com.example.apolloproject.ui.screens.splashscreen.SplashScreen
import com.example.fitgo.R
import com.example.fitgo.domain.model.Dieta
import com.example.fitgo.domain.model.Entrenamientos
import com.example.fitgo.ui.chat.ChatViewModel
import com.example.fitgo.ui.chat.PantallaChat
import com.example.fitgo.ui.dietas.DietasLista
import com.example.fitgo.ui.dietas.dietasdetalle.DietaDetalle
import com.example.fitgo.ui.entrenamientoscompose.EntrenamientosLista
import com.example.fitgo.ui.entrenamientoscompose.entrenamientosdetalles.EntrenamientosDetalle

@Composable

fun navigationAct() {
    val navController = rememberNavController()
    val splashRoute = stringResource(id = R.string.splash)
    val pantallaLogin = stringResource(id = R.string.pantallaLogin)
    val entrenamientosList = stringResource(id = R.string.entrenamientosPath)
    val entrenDetalle = stringResource(id = R.string.detalleEntrenamiento)
    val entrenamientoId = stringResource(id = R.string.entrenamiento)
    val dietaId = stringResource(id = R.string.dietaId)
    val dietaList = stringResource(id = R.string.dietasPath)
    val dietaDetallePath= stringResource(id = R.string.detalleDieta)
    val chatConChatgpt= stringResource(id = R.string.chatConGpt)
    NavHost(
        navController = navController,
        startDestination = splashRoute,

        ) {
        composable(
            splashRoute
        ) {
            SplashScreen(navController = navController)
        }
        composable(pantallaLogin) {
            pantallaLogin(navController)
        }
        composable(
            entrenamientosList
        ) {
            EntrenamientosLista(
                navController,
                onViewDetalle = { entrenamientoId ->
                    navController.navigate("detalleEntren/${entrenamientoId}")
                },
                bottomNavigationBar = {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar
                    )
                }
            )

        }

        composable(
            route = entrenDetalle,
            arguments = listOf(
                navArgument(name = entrenamientoId) {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            EntrenamientosDetalle(
                entrenamientosId = it.arguments?.getInt(entrenamientoId) ?: 0

            )

        }

        composable(
            route = dietaDetallePath,
            arguments = listOf(
                navArgument(name = dietaId) {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            DietaDetalle(
                dietaId=it.arguments?.getInt(dietaId) ?: 0
            )

        }
        composable(
            dietaList

        ) {
            DietasLista(
                navController,
                onViewDetalle = { dietaId ->
                    navController.navigate("detalleDieta/${dietaId}")
                },
                bottomNavigationBar = {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar
                    )
                }

            )
        }
        composable(
            chatConChatgpt
        ){

            PantallaChat(

            )
        }
    }
}