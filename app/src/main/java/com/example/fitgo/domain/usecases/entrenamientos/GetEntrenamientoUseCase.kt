package com.example.fitgo.domain.usecases.entrenamientos

import com.example.fitgo.data.EntrenamientosRepository
import javax.inject.Inject

class GetEntrenamientoUseCase @Inject constructor(
    val entrenamientosRepository: EntrenamientosRepository

) {
    fun invoke(id:Int)=entrenamientosRepository.getEntrenamiento(id)

}