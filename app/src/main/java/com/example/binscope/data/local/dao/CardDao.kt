package com.example.binscope.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.binscope.data.local.entity.CardEntity

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertCard(card: CardEntity)

    @Query("SELECT * FROM cards")
    suspend fun getAllCards(): List<CardEntity>
}