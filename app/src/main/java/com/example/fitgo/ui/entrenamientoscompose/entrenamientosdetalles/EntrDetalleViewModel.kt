package com.example.fitgo.ui.entrenamientoscompose.entrenamientosdetalles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitgo.domain.usecases.ejercicios.GetEjerciciosByEntrenamientoUseCase
import com.example.fitgo.domain.usecases.entrenamientos.GetEntrenamientoUseCase
import com.example.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntrDetalleViewModel @Inject constructor(
   private val getEntrenamientoUseCase: GetEntrenamientoUseCase,
    private val getEjerciciosByEntrenamientoUseCase: GetEjerciciosByEntrenamientoUseCase
):ViewModel() {
    private val _uiState = MutableStateFlow((EntrDetalleContract.State()))
    val uiState: StateFlow<EntrDetalleContract.State> = _uiState.asStateFlow()
    fun event(event:EntrDetalleContract.Event){
        when(event){

            is EntrDetalleContract.Event.GetEntrenamiento -> getEntrenamiento(event.entrenamientoId)
            is EntrDetalleContract.Event.GetEjercicios -> cargarEjercicios(event.entrenamientoId)
        }
    }
    fun cargarEjercicios(id:Int){
        viewModelScope.launch {

            getEjerciciosByEntrenamientoUseCase.invoke(id)

                .collect{result->
                    when(result){
                        is NetworkResult.Error ->  {
                            _uiState.update {
                                it.copy(
                                    message = result.message,

                                    )
                            }
                        }
                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResult.Success -> _uiState.update {
                            it.copy(
                              ejercicios = result.data
                                  ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }

        }
    }


    fun getEntrenamiento(entrenamientoId:Int){
        viewModelScope.launch {

            getEntrenamientoUseCase.invoke(entrenamientoId)

                .collect{result->
                    when(result){
                        is NetworkResult.Error ->  {
                            _uiState.update {
                                it.copy(
                                    message = result.message,

                                    )
                            }
                        }
                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResult.Success -> _uiState.update {
                            it.copy(
                                entrenamiento = result.data,
                                 isLoading = false
                            )
                        }
                    }
                }

        }
    }
}