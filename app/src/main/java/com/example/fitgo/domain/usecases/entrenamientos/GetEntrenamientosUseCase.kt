package com.example.fitgo.domain.usecases.entrenamientos

import com.example.fitgo.data.EntrenamientosRepository
import javax.inject.Inject

class GetEntrenamientosUseCase @Inject constructor(
    val entrenamientosRepository: EntrenamientosRepository

){
     fun invoke(email:String)=entrenamientosRepository.getEntrenamientos(email)
}