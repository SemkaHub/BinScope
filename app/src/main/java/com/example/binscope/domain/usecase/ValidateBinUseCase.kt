package com.example.binscope.domain.usecase

import javax.inject.Inject

const val MIN_BIN_LENGTH = 6

class ValidateBinUseCase @Inject constructor() {
    operator fun invoke(bin: String) = bin.length >= MIN_BIN_LENGTH
}