package com.example.toyproject.model.domain

import com.example.toyproject.model.domain.Owner

data class GitItem (
    val full_name: String,

    val language: String?,

    val owner: Owner,

    val stargazers_count: Int,

    val description: String?,

    val updated_at: String
)