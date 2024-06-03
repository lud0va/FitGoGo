package com.example.fitgo.ui.entrenamientoscompose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.fitgo.R
import com.example.fitgo.domain.model.Entrenamientos

@Composable
fun EntrenamientosLista(
    navController: NavController,
    viewModel: EntrenamientosViewModel = hiltViewModel(),
    onViewDetalle: (Int) -> Unit,
    bottomNavigationBar: @Composable () -> Unit = {}
){

    val state = viewModel.state.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        viewModel.event(EntrenamientosContract.Event.getEntrenamientos)
    }



    ListaEntrenamientos(
        navController = navController,
        state = state.value,
        onViewDetalle = onViewDetalle,
        bottomNavigationBar = bottomNavigationBar,
    )


}
@Composable
fun ListaEntrenamientos(
    navController: NavController,
    state: EntrenamientosContract.State,
    onViewDetalle: (Int) -> Unit,
    bottomNavigationBar: @Composable () -> Unit = {},
){
    val snackbarHostState = remember { SnackbarHostState() }
    val label= stringResource(id = R.string.ok)
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomNavigationBar,
    ){ innerPadding ->
        LaunchedEffect(state.message) {
            state.message?.let {
                snackbarHostState.showSnackbar(
                    message = it,
                    actionLabel = label
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.Gray)
        ) {

            items(items = state.entrenamientos, key = { entr -> entr.id }) { entr ->
                    EntrenamientoItem(entrenamiento = entr, onViewDetalle = onViewDetalle)





            }

            }
        }
    }


@Composable
fun EntrenamientoItem(

    entrenamiento: Entrenamientos,
    onViewDetalle: (Int) -> Unit,
    modifier: Modifier = Modifier

) {

    Card(modifier = modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.dimen_8dp))
        .clickable { onViewDetalle(entrenamiento.id) }) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_8dp)) ) {
            Text(
                modifier = Modifier.weight(weight = 0.4F),
                text = entrenamiento.diaSemana
            )
            Text(
                modifier = Modifier.weight(weight = 0.4F),
                text = entrenamiento.tipo
            )


        }
    }

}