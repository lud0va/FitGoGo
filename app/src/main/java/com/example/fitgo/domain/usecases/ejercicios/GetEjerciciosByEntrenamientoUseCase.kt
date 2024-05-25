package com.example.fitgo.domain.usecases.ejercicios

import com.example.fitgo.data.EjerciciosRepository
import com.example.fitgo.data.EntrenamientosRepository
import javax.inject.Inject

class GetEjerciciosByEntrenamientoUseCase @Inject constructor(
    val ejerciciosRepository: EjerciciosRepository
) {
    fun invoke(id:Int)=ejerciciosRepository.getEjerciciosByEntrenamiento(id)

}