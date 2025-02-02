package com.example.binscope.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardData(
    val timestamp: Long,
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
) : Parcelable
