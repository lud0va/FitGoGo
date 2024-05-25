package com.example.fitgo.domain.usecases.dieta

import com.example.fitgo.data.DietasRepository
import javax.inject.Inject

class GetDietaUseCase @Inject constructor(
      val dietasRepository: DietasRepository
) {
    fun invoke(id:Int)=dietasRepository.getDieta(id)
}