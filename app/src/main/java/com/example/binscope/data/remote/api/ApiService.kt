package com.example.binscope.data.remote.api

import com.example.binscope.data.remote.dto.CardResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{bin}")
    suspend fun getCardByBin(@Path("bin") bin: String): CardResponseDto
}