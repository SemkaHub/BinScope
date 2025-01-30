package com.example.binscope.domain.model

data class CardData(
    val scheme: String,
    val type: String,
    val brand: String,
    val prepaid: String,
    val countryName: String,
    val latitude: String,
    val longitude: String,
    val bankName: String,
    val bankUrl: String,
    val bankPhone: String,
    val bankCity: String
)
