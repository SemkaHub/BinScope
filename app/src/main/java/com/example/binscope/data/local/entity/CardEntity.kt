package com.example.binscope.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
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
