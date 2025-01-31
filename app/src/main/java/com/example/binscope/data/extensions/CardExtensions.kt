package com.example.binscope.data.extensions

import com.example.binscope.data.local.entity.CardEntity
import com.example.binscope.data.remote.dto.CardResponseDto
import com.example.binscope.domain.model.CardData

internal fun CardEntity.toDomain() = CardData(
    bin = this.bin,
    scheme = this.scheme,
    type = this.type,
    brand = this.brand,
    prepaid = this.prepaid,
    countryName = this.countryName,
    latitude = this.latitude,
    longitude = this.longitude,
    bankName = this.bankName,
    bankUrl = this.bankUrl,
    bankPhone = this.bankPhone,
    bankCity = this.bankCity
)

internal fun CardResponseDto.toEntity(bin: String) = CardEntity(
    bin = bin,
    scheme = this.scheme,
    type = this.type,
    brand = this.brand,
    prepaid = this.prepaid,
    countryName = this.country.name,
    latitude = this.country.latitude.toString(),
    longitude = this.country.longitude.toString(),
    bankName = this.bank.name,
    bankUrl = this.bank.url,
    bankPhone = this.bank.phone,
    bankCity = this.bank.city
)