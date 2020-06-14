package com.example.toyproject.db

import androidx.room.*
import com.example.toyproject.model.domain.GitItem
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface HistoryDao {
    @Query("SELECT * FROM GitItem")
    fun getAll(): List<GitItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repo: GitItem)

    @Delete
    fun delete(repo: GitItem)
}