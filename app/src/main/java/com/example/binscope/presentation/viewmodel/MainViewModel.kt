package com.example.binscope.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binscope.domain.model.CardData
import com.example.binscope.domain.usecase.GetCardByBinUseCase
import com.example.binscope.domain.usecase.ValidateBinUseCase
import com.example.binscope.presentation.state.AppError
import com.example.binscope.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCardByBinUseCase: GetCardByBinUseCase,
    private val validateBinUseCase: ValidateBinUseCase
) : ViewModel() {

    private var _state = MutableStateFlow<UiState<CardData>>(UiState.Empty)
    val state: StateFlow<UiState<CardData>> = _state

    private var lastBin: String? = null

    fun loadCard(bin: String) {
        lastBin = bin
        if (!validateBinUseCase.invoke(bin)) {
            // Время добавлено в качестве метки, чтобы состоянии валидации всегда обновлялось
            _state.value = UiState.Error(AppError.ValidationError(System.currentTimeMillis()))
            return
        }

        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val card = getCardByBinUseCase.invoke(bin)
                _state.value = UiState.Success(card)
            } catch (e: IOException) {
                Log.e("MainViewModel", e.message, e)
                _state.value = UiState.Error(AppError.NetworkError)
            } catch (e: HttpException) {
                Log.e("MainViewModel", e.message, e)
                _state.value = UiState.Error(AppError.ServerError)
            } catch (e: Exception) {
                Log.e("MainViewModel", e.message, e)
                _state.value = UiState.Error(AppError.UnknownError)
            }
        }
    }

    fun retry() {
        lastBin?.let { loadCard(it) }
    }
}