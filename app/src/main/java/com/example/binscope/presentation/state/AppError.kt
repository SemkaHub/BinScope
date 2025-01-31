package com.example.binscope.presentation.state

sealed class AppError {
    object NetworkError : AppError()
    object ServerError : AppError()
    data class ValidationError(val timestamp: Long) : AppError()
    object UnknownError : AppError()
}