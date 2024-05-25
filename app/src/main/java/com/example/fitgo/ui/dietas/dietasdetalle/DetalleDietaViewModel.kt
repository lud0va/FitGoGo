package com.example.fitgo.ui.dietas.dietasdetalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitgo.domain.usecases.alimentospermitidos.GetAlimentosPermitidosDietaUseCase
import com.example.fitgo.domain.usecases.dieta.GetDietaUseCase
import com.example.fitgo.domain.usecases.plato.GetPlatosDietaUseCase
import com.example.fitgo.ui.dietas.DietasContract
import com.example.fitgo.ui.entrenamientoscompose.entrenamientosdetalles.EntrDetalleContract
import com.example.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleDietaViewModel  @Inject constructor(
  private val getAlimentosPermitidosDietaUseCase: GetAlimentosPermitidosDietaUseCase,
  private val  getPlatosDietaUseCase: GetPlatosDietaUseCase,
  private val getDietaUseCase: GetDietaUseCase
):ViewModel(){
  private val _uiState = MutableStateFlow((DetalleDietaContract.State()))
  val uiState: StateFlow<DetalleDietaContract.State> = _uiState.asStateFlow()

  fun event(event: DetalleDietaContract.Event){
    when(event){
      is DetalleDietaContract.Event.GetAlimentosPermitidos -> cargarAlimentosPermitidos(event.dietaId)
      is DetalleDietaContract.Event.GetDieta -> cargarDieta(event.dietaId)
      is DetalleDietaContract.Event.GetPlatos -> cargarPlatos(event.dietaId)
    }
  }

  fun cargarAlimentosPermitidos(iddieta:Int){
    viewModelScope.launch {

      getAlimentosPermitidosDietaUseCase.invoke(iddieta)

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
                alimentosPermitidos  = result.data  ?: emptyList(),
                isLoading = false
              )
            }
          }
        }

    }
  }
  fun cargarPlatos(iddieta:Int){
    viewModelScope.launch {

      getPlatosDietaUseCase.invoke(iddieta)

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
                platos  = result.data  ?: emptyList(),
                isLoading = false
              )
            }
          }
        }

    }
  }
  fun cargarDieta(iddieta:Int){
    viewModelScope.launch {

      getDietaUseCase.invoke(iddieta)

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
                dieta  = result.data,
                isLoading = false
              )
            }
          }
        }

    }
  }





}