package com.example.fitgo.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.apolloproject.ui.screens.logincompose.LoginContract
import com.example.apolloproject.ui.screens.logincompose.coachCode
import com.example.apolloproject.ui.screens.logincompose.emailUsuario
import com.example.apolloproject.ui.screens.logincompose.loginBtn
import com.example.apolloproject.ui.screens.logincompose.nameUser
import com.example.apolloproject.ui.screens.logincompose.passwUsuario
import com.example.apolloproject.ui.screens.logincompose.registerBtn
import com.example.apolloproject.ui.screens.logincompose.switchMode
import com.example.fitgo.R
import com.example.fitgo.ui.entrenamientoscompose.entrenamientosdetalles.EntrDetalleContract
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun mainScreen(

    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
    onViewEntrenamiento: (Int) -> Unit,



    bottomNavigationBar: @Composable () -> Unit = {}
) {
    val calendar: Calendar = Calendar.getInstance()
    val date: Date = calendar.getTime()


    val dayFormat = SimpleDateFormat("EEEE", Locale("es", "ES"))
    val dayOfWeek: String = dayFormat.format(date)

    val state = viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.event(MainContract.Event.cargarUsername())
        viewModel.event(MainContract.Event.getEntrenamiento(dayOfWeek))

    }

    screenContent(
        navController=navController,
        onViewEntrenamiento = onViewEntrenamiento,

        state = state.value,
        bottomNavigationBar=bottomNavigationBar
    )

}

@Composable
fun screenContent(
    navController: NavController,

    onViewEntrenamiento: (Int) -> Unit,

    state: MainContract.State,
    bottomNavigationBar: @Composable () -> Unit = {},
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val label = stringResource(id = R.string.ok)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = bottomNavigationBar,
    ) { innerPadding ->

        LaunchedEffect(state.message) {
            state.message?.let {

                snackbarHostState.showSnackbar(
                    message = it,
                    actionLabel = label
                )
            }

        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            mainMesage(state = state)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))

            entrenamientoToday(state = state, onViewEntrenamiento = onViewEntrenamiento)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))

            entrenamientosSem(navController=navController, state = state)

        }
    }


}

@Composable
fun entrenamientoToday(


    state: MainContract.State,
    onViewEntrenamiento: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(modifier = modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.dimen_8dp))
        .clickable { state.entrenamiento?.id?.let { onViewEntrenamiento(it) } }) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_8dp))) {

            state.entrenamiento?.diaSemana?.let {
                Text(
                    modifier = Modifier.weight(0.4f),
                    text = it,
                    style = TextStyle(
                        fontSize = 14.sp
                    )
                )
            }
            Text(
                modifier = Modifier.weight(0.2f),
                text = "entrenamiento del día"
            )
            state.entrenamiento?.tipo?.let {
                Text(
                    modifier = Modifier.weight(0.2f),
                    text = it,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
            }
        }


        }
    }




@Composable
fun mainMesage(
    state: MainContract.State
) {
    state.username?.let {
        TextField(
            value = "Bienvenido "+it,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {},
            singleLine = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth()

        )
    }

}

@Composable
fun entrenamientosSem(
    navController: NavController,
    state: MainContract.State,
    modifier: Modifier = Modifier
) {
    val entrenamientosList = stringResource(id = R.string.entrenamientosPath)

    Button(

        onClick = { navController.navigate(entrenamientosList)},
        enabled = true,
        content = { Text(text = stringResource(id = R.string.verEntrenamientos)) })


}