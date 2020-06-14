package com.example.toyproject.db

import android.content.Context
import androidx.room.Room

class DatabaseManager {
    private fun getRepoDatabaseInstance(context: Context): HistoryDatabase {
        return Room.databaseBuilder(context.applicationContext,
            HistoryDatabase::class.java, "git_repo.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    fun getHistoryDao(context: Context): HistoryDao = getRepoDatabaseInstance(context).historyDao()
}