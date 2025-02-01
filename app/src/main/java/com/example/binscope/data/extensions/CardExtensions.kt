package com.example.binscope.data.extensions

import com.example.binscope.data.local.entity.CardEntity
import com.example.binscope.data.remote.dto.CardResponseDto
import com.example.binscope.domain.model.CardData

internal const val NO_DATA_VALUE = "no data"

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
    bankName = this.bank.name?.toString() ?: NO_DATA_VALUE,
    bankUrl = this.bank.url?.toString() ?: NO_DATA_VALUE,
    bankPhone = this.bank.phone?.toString() ?: NO_DATA_VALUE,
    bankCity = this.bank.city?.toString() ?: NO_DATA_VALUE
)