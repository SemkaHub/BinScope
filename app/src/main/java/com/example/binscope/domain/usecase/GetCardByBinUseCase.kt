package com.example.binscope.domain.usecase

import com.example.binscope.domain.repository.CardRepository
import javax.inject.Inject

class GetCardByBinUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(bin: String) = cardRepository.getCardByBin(bin)
}