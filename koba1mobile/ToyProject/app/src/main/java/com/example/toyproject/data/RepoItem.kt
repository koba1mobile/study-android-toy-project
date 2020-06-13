package com.example.toyproject.data

import com.google.gson.annotations.SerializedName

data class RepoItem (
    val full_name: String,

    val language: String?,

    val owner: Owner,

    val stargazers_count: Int,

    val description: String?,

    val updated_at: String
)