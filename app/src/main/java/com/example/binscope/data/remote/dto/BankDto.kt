package com.example.binscope.data.remote.dto

data class BankDto(
    val name: String,
    val url: String? = "-",
    val phone: String? = "-",
    val city: String? = "-"
)
