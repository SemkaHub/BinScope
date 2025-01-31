package com.example.binscope.data.di

import com.example.binscope.data.local.dao.CardDao
import com.example.binscope.data.remote.api.ApiService
import com.example.binscope.data.repository.CardRepositoryImpl
import com.example.binscope.domain.repository.CardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideCardRepository(apiService: ApiService, cardDao: CardDao): CardRepository {
        return CardRepositoryImpl(apiService, cardDao)
    }
}