package com.example.toyproject.model.domain

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.toyproject.common.base.list.ItemData
import com.google.gson.annotations.SerializedName

@Entity(tableName = "GitItem")
data class GitItem(
    @PrimaryKey
    @ColumnInfo(name = "full_name")
    val full_name: String,

    @ColumnInfo(name = "language")
    val language: String?,

    @Embedded
    @ColumnInfo(name = "owner")
    val owner: Owner,

    val stargazers_count: Int,

    val description: String?,

    val updated_at: String
) : ItemData