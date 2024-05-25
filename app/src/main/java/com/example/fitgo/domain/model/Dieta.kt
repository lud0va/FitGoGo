package com.example.fitgo.domain.model

import java.time.LocalDate

data class Dieta(
    val id:Int,
    val fija:String,
    val caloriasmaxdia:String,
    val finDieta:LocalDate,
    val platoDeDieta:List<Plato>,
    val alimentosPermitidos:List<AlimentosPermitidos>

)