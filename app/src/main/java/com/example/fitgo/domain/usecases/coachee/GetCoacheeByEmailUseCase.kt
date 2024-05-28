package com.example.fitgo.domain.usecases.coachee

import com.example.fitgo.data.CoacheeRepository
import javax.inject.Inject

class GetCoacheeByEmailUseCase   @Inject constructor(
    val coacheeRepository: CoacheeRepository

) {
    fun invoke(email:String)=coacheeRepository.getCoacheeByEmail(email)

}