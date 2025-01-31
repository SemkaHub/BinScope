package com.example.binscope.domain.di

import com.example.binscope.domain.repository.CardRepository
import com.example.binscope.domain.usecase.GetCardByBinUseCase
import com.example.binscope.domain.usecase.GetHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun provideGetCardByBinUseCase(cardRepository: CardRepository) =
        GetCardByBinUseCase(cardRepository)

    @Provides
    fun provideGetHistoryUseCase(cardRepository: CardRepository) =
        GetHistoryUseCase(cardRepository)
}