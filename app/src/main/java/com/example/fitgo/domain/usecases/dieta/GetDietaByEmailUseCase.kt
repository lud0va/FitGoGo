package com.example.fitgo.domain.usecases.dieta

import com.example.fitgo.data.DietasRepository
import com.example.fitgo.data.EjerciciosRepository
import javax.inject.Inject

class GetDietaByEmailUseCase @Inject constructor(
    val dietasRepository: DietasRepository
) {
    fun invoke(email:String)=dietasRepository.getDietasByEmail(email)

}