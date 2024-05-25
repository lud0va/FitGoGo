package com.example.fitgo.domain.usecases.alimentospermitidos

import com.example.fitgo.data.AlimentosPermitidosRepository
import com.example.fitgo.data.DietasRepository
import javax.inject.Inject

class GetAlimentosPermitidosDietaUseCase @Inject constructor(
val alimentosPermitidosRepository: AlimentosPermitidosRepository
)  {
    fun invoke(id:Int)=alimentosPermitidosRepository.getAlimentosPermitido(id)

}