package com.example.binscope.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binscope.domain.model.CardData
import com.example.binscope.domain.usecase.GetHistoryUseCase
import com.example.binscope.presentation.state.AppError
import com.example.binscope.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<CardData>>>(UiState.Loading)
    val state: StateFlow<UiState<List<CardData>>> = _state

    init {
        loadHistory()
    }

    fun loadHistory() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val history = getHistoryUseCase()
                if (history.isNotEmpty()) {
                    val sortedHistory = history.sortedByDescending { it.timestamp }
                    _state.value = UiState.Success(sortedHistory)
                } else {
                    _state.value = UiState.Empty
                }
            } catch (e: Exception) {
                Log.e("HistoryViewModel", "Error loading history. Message: ${e.message}", e)
                _state.value = UiState.Error(AppError.UnknownError)
            }
        }
    }
}