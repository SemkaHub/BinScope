package com.example.binscope.presentation.state

import com.example.binscope.domain.model.CardData

sealed class UiState {
    object Loading : UiState()
    object Empty : UiState()
    data class Success(val card: CardData) : UiState()
    data class Error(val error: AppError) : UiState()
}