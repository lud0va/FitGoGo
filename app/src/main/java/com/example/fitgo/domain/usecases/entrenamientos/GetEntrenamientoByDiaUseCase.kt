package com.example.fitgo.domain.usecases.entrenamientos

import com.example.fitgo.data.EntrenamientosRepository
import javax.inject.Inject

class GetEntrenamientoByDiaUseCase @Inject constructor(
    val entrenamientosRepository: EntrenamientosRepository

){
    fun invoke(dia:String)=entrenamientosRepository.getEntrenamientoByDia(dia)
}