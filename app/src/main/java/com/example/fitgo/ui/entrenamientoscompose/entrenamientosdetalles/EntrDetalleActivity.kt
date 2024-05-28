package com.example.fitgo.ui.entrenamientoscompose.entrenamientosdetalles

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitgo.R
import com.example.fitgo.domain.model.Ejercicios
import com.example.fitgo.domain.model.Entrenamientos
import com.example.fitgo.ui.entrenamientoscompose.EntrenamientoItem

@Composable
fun EntrenamientosDetalle(
    entrenamientosId: Int,
    viewModel: EntrDetalleViewModel= hiltViewModel()
){
val state = viewModel.uiState.collectAsStateWithLifecycle()
    if(state.value.entrenamiento==null){
        viewModel.event(EntrDetalleContract.Event.GetEntrenamiento(entrenamientosId))
        viewModel.event(EntrDetalleContract.Event.GetEjercicios(entrenamientosId))
    }
    Box(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dimen_8dp))
    ) {

        ContenidoCustPantalla(
            state.value,
            Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.dimen_8dp)),


        )
    }
}
@Composable
fun ContenidoCustPantalla(

    state: EntrDetalleContract.State,
    align: Modifier,


) {
    Column(modifier = align) {




        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))
        diaEntrenamiento(state)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))

        Text(text = stringResource(id = R.string.ejercicios))
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))
        listaEjercicos(
            state = state,

            )




    }


}
@Composable
fun listaEjercicos(
   state: EntrDetalleContract.State
){
    LazyColumn(
        modifier = Modifier

            .fillMaxSize()
            .background(Color.White)
    ) {
            if(!state.ejercicios.isEmpty()){
                items(items = state.ejercicios, key = { entr -> entr.id }) { entr ->
                    ejercicioItem(ejercicio = entr)
            }









        }
    }
}

@Composable
fun ejercicioItem(
    ejercicio: Ejercicios,
    modifier: Modifier = Modifier

){
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.dimen_8dp))
        ) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_8dp)) ) {
            if(ejercicio.nombre!=null && ejercicio.descripcion!=null){
                Text(
                    modifier = Modifier.weight(weight = 0.4F),
                    text = ejercicio.nombre
                )
                Text(
                    modifier = Modifier.weight(weight = 0.4F),
                    text = ejercicio.descripcion

                )
            }



        }
}
}
@Composable
fun diaEntrenamiento(
    state: EntrDetalleContract.State
){
    state.entrenamiento?.diaSemana?.let { lastname ->
        TextField(
            value = lastname,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {},
            singleLine = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth()

        )
    }
}
