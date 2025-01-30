package com.example.binscope.domain.repository

import com.example.binscope.domain.model.CardData

interface CardRepository {
    suspend fun getCardByBin(bin: String): CardData
    suspend fun getHistory(): List<CardData>
}