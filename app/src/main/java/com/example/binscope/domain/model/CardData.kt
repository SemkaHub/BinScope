package com.example.binscope.domain.model

data class CardData(
    val bin: String,
    val scheme: String,
    val type: String,
    val brand: String,
    val prepaid: Boolean,
    val countryName: String,
    val latitude: String,
    val longitude: String,
    val bankName: String,
    val bankUrl: String,
    val bankPhone: String,
    val bankCity: String
)
