package com.example.toyproject.data

import com.google.gson.annotations.SerializedName

data class SearchResponse (
    val total_count: Int,

    @SerializedName("items")
    val repoItems: List<RepoItem>
)
