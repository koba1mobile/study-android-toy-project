package com.example.toyproject.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.toyproject.model.domain.GitItem

@Database(entities = [GitItem::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}