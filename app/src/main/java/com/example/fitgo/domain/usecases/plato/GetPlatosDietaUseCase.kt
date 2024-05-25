package com.example.fitgo.domain.usecases.plato

import com.example.fitgo.data.AlimentosPermitidosRepository
import com.example.fitgo.data.PlatosRepository
import javax.inject.Inject

class GetPlatosDietaUseCase @Inject constructor(
    val platosRepository: PlatosRepository
)  {
    fun invoke(id:Int)=platosRepository.getPlatosByDieta(id)

}