package com.example.apolloproject.ui.screens.logincompose




import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

import androidx.navigation.compose.rememberNavController
import com.example.fitgo.R


@Composable
fun pantallaLogin(
    navController: NavController = rememberNavController(),
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Box(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dimen_16dp))
    ) {

        ContenidoPantalla(
            navController,
            state,
            viewModel,
            { viewModel.event(LoginContract.Event.login) },
            { viewModel.event(LoginContract.Event.register) },
            { viewModel.event(LoginContract.Event.CambiarPasswState(it)) },
            { viewModel.event(LoginContract.Event.CambiarUserState(it)) },
            { viewModel.event(LoginContract.Event.CambiarNameState(it)) },
            { viewModel.event(LoginContract.Event.CambiarRegisterModeSuccess(it)) }


        )
    }

}

@Composable
fun ContenidoPantalla(
    navController: NavController,
    state: LoginContract.State,
    viewModel: LoginViewModel? = null,

    login: () -> Unit,
    regist: () -> Unit,
    cambiarPassw: (String) -> Unit,
    cambiarUser: (String) -> Unit,
    cambiarName:(String) ->Unit,
    cambiarRegisterMode:(Boolean)->Unit


) {
    val label= stringResource(id = R.string.ok)
    val navigateToEntrenamientosList= stringResource(id = R.string.entrenamientosPath)
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButtonPosition = FabPosition.Center
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

            switchMode(state,  cambiarRegisterMode)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_16dp)))
            emailUsuario(state, cambiarUser)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))
            passwUsuario(state, cambiarPassw)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))
            if (state.registerMode){
                nameUser(state, cambiarName)
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_6dp)))

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_6dp)))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.dimen_16dp)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                loginBtn(login,state)
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimen_16dp)))
                registerBtn(regist,state)


            }

            if (state.loginsucces) {
                LaunchedEffect(state.loginsucces) {
                    viewModel?.event(LoginContract.Event.CambiarLoginSuccess(false))

                    navController.navigate(navigateToEntrenamientosList)
                }
            }


        }
    }
}

@Composable
fun switchMode(state: LoginContract.State,cambiarRegisterMode: (Boolean) -> Unit) {


    Switch(
        checked = state.registerMode,

        onCheckedChange = {
           cambiarRegisterMode(it)
        },
        thumbContent = if (state.registerMode) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        } else {
            null
        }

    )
}
@Composable
fun emailUsuario(state: LoginContract.State, cambiarUser: (String) -> Unit) {



        TextField(
            value = state.email?:"",
            placeholder = { Text(text = stringResource(id = R.string.email)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                cambiarUser(it)
                            },
            singleLine = true,
            enabled = true,
            modifier = Modifier.fillMaxWidth()

        )


}

@Composable
fun loginBtn(login: () -> Unit,state: LoginContract.State) {

        Button(

            onClick = {login() },
            enabled=!state.registerMode,
            content = { Text(text = stringResource(id = R.string.login)) })




}


@Composable
fun registerBtn(regist: () -> Unit,state: LoginContract.State) {

    Button(onClick = { regist()},
        enabled=state.registerMode,

        content = { Text(text = stringResource(id = R.string.register)) })


}

@Composable
fun passwUsuario(state: LoginContract.State, cambiarPassw: (String) -> Unit) {



        TextField(
            value = state.password?:"",
            placeholder = { Text(text = stringResource(id = R.string.passw)) },

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                cambiarPassw(it)

            },
            singleLine = true,
            enabled = true,
            modifier = Modifier.fillMaxWidth()

        )

}

@Composable
fun nameUser(state: LoginContract.State, cambiarName: (String) -> Unit) {



    TextField(
        value = state.name?:"",
        placeholder = { Text(text = stringResource(id = R.string.username)) },

        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = {
            cambiarName(it)

        },
        singleLine = true,
        enabled = true,
        modifier = Modifier.fillMaxWidth()

    )

}


