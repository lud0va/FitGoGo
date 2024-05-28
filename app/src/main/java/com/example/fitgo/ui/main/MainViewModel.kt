package com.example.fitgo.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apolloproject.ui.screens.logincompose.LoginContract
import com.example.fitgo.domain.usecases.coachee.GetCoacheeByEmailUseCase
import com.example.fitgo.domain.usecases.entrenamientos.GetEntrenamientoByDiaUseCase
import com.example.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
   val getEntrenamientoByDiaUseCase: GetEntrenamientoByDiaUseCase,
    val getCoacheeByEmailUseCase: GetCoacheeByEmailUseCase,
   val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainContract.State())
    val uiState: StateFlow<MainContract.State> = _uiState.asStateFlow()

    fun event(event: MainContract.Event) {

        when (event) {
            is MainContract.Event.cargarUsername -> cargarUsername()
            is MainContract.Event.getEntrenamiento -> cargarEntrenamiento(event.dia)
        }
    }
    fun cargarEntrenamiento(dia:String){
        viewModelScope.launch {


                getEntrenamientoByDiaUseCase.invoke(dia)
                    .collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _uiState.update {
                                    it.copy(
                                        message = result.message,

                                        )
                                }

                            }

                            is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                            is NetworkResult.Success -> _uiState.update {
                                it.copy(
                                    entrenamiento =   result.data
                                    , isLoading = false
                                )
                            }

                        }


                    }
            }





    }

    fun cargarUsername(){
        viewModelScope.launch {

            sharedPreferences.getString("userEmail", null)?.let {
                getCoacheeByEmailUseCase.invoke(it)
                    .collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _uiState.update {
                                    it.copy(
                                        message = result.message,

                                        )
                                }

                            }

                            is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                            is NetworkResult.Success -> _uiState.update {
                                it.copy(
                                    username =   result.data?.username
                                       , isLoading = false
                                )
                            }

                        }


                    }
            }

        }



    }


}



