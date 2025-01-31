package com.example.binscope.data.repository

import com.example.binscope.data.extensions.toDomain
import com.example.binscope.data.extensions.toEntity
import com.example.binscope.data.local.dao.CardDao
import com.example.binscope.data.remote.api.ApiService
import com.example.binscope.domain.model.CardData
import com.example.binscope.domain.repository.CardRepository
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val cardDao: CardDao
) : CardRepository {

    override suspend fun getCardByBin(bin: String): CardData {
        return try {
            val card = apiService.getCardByBin(bin)
            val entity = card.toEntity(bin)
            cardDao.insertCard(entity)
            entity.toDomain()
        } catch (e: Exception) {
            throw Exception("Error fetching card data. Message: ${e.message}")
        }
    }

    override suspend fun getHistory(): List<CardData> =
        cardDao.getAllCards().map { it.toDomain() }
}