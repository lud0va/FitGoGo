package com.example.fitgo.ui.dietas.dietasdetalle

import androidx.compose.foundation.background
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
import com.example.fitgo.domain.model.AlimentosPermitidos
import com.example.fitgo.domain.model.Ejercicios
import com.example.fitgo.domain.model.Plato
import com.example.fitgo.ui.entrenamientoscompose.entrenamientosdetalles.ContenidoCustPantalla
import com.example.fitgo.ui.entrenamientoscompose.entrenamientosdetalles.EntrDetalleContract
import com.example.fitgo.ui.entrenamientoscompose.entrenamientosdetalles.EntrDetalleViewModel
import com.example.fitgo.ui.entrenamientoscompose.entrenamientosdetalles.diaEntrenamiento
import com.example.fitgo.ui.entrenamientoscompose.entrenamientosdetalles.ejercicioItem
import com.example.fitgo.ui.entrenamientoscompose.entrenamientosdetalles.listaEjercicos

@Composable
fun DietaDetalle(
    dietaId: Int,
    viewModel: DetalleDietaViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    if (state.value.dieta == null) {
        viewModel.event(DetalleDietaContract.Event.GetDieta(dietaId))
        viewModel.event(DetalleDietaContract.Event.GetPlatos(dietaId))
        viewModel.event(DetalleDietaContract.Event.GetAlimentosPermitidos(dietaId))

    }
    Box(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dimen_8dp))
    ) {

        ContenidoDietaPantalla(
            state.value,
            Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.dimen_8dp)),


            )
    }


}

@Composable
fun ContenidoDietaPantalla(

    state: DetalleDietaContract.State,
    align: Modifier,


    ) {
    Column(modifier = align) {


        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))
        caloriasMaxDia(state)
        finDieta(state)


        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))


        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))


        Column {
            Text(text = stringResource(id = R.string.alimentosPerm))

            listaAlimentos(
                state = state,

                )
            Text(text = stringResource(id = R.string.platos))
            listaPlatos(
                state = state
            )

        }


    }


}

@Composable
fun caloriasMaxDia(
    state: DetalleDietaContract.State,

    ) {
    state.dieta?.caloriasmaxdia?.let { caloriasmaxdia ->
        TextField(
            value = caloriasmaxdia,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {},
            singleLine = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth()

        )
    }
}

@Composable
fun finDieta(
    state: DetalleDietaContract.State,
) {
    state.dieta?.finDieta?.let { findieta ->
        TextField(
            value = findieta.toString(),

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {},
            singleLine = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth()

        )
    }
}

@Composable
fun listaAlimentos(
    state: DetalleDietaContract.State,
) {
    LazyColumn(
        modifier = Modifier

            .fillMaxSize()
            .background(Color.Gray)
    ) {

        items(items = state.alimentosPermitidos, key = { alims -> alims.id }) { alims ->
            alimentosPermitidosItem(alimentosPerm = alims)





        }
    }
}

@Composable
fun listaPlatos(
    state: DetalleDietaContract.State,
) {
    LazyColumn(
        modifier = Modifier

            .fillMaxSize()
            .background(Color.Gray)
    ) {

        items(items = state.platos, key = { plato -> plato.id }) { plato ->
            platoItem(plato = plato)





        }
    }
}

@Composable
fun platoItem(
    plato: Plato,
    modifier: Modifier = Modifier

){
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.dimen_8dp))
    ) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_8dp)) ) {

            Text(
                modifier = Modifier.weight(weight = 0.4F),
                text = plato.nombre
            )
            Text(
                modifier = Modifier.weight(weight = 0.4F),
                text = plato.desc

            )


        }
    }
}
@Composable
fun alimentosPermitidosItem(
    alimentosPerm: AlimentosPermitidos,
    modifier: Modifier = Modifier

){
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.dimen_8dp))
    ) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_8dp)) ) {

            Text(
                modifier = Modifier.weight(weight = 0.4F),
                text = alimentosPerm.alimentos.nombre
            )
            Text(
                modifier = Modifier.weight(weight = 0.4F),
                text = alimentosPerm.cantidad.toString()

            )


        }
    }
}