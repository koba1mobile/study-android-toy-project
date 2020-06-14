package com.example.toyproject.model.domain

import com.google.gson.annotations.SerializedName

data class SearchResponse (
    val total_count: Int,

    @SerializedName("items")
    val gitItems: List<GitItem>
)
