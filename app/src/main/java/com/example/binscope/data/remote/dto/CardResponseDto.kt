package com.example.binscope.data.remote.dto

data class CardResponseDto(
    val scheme: String,
    val type: String,
    val brand: String,
    val prepaid: Boolean,
    val country: CountryDto,
    val bank: BankDto
)
