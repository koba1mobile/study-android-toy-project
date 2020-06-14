package com.example.toyproject.model.domain

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("avatar_url")
    val thumbnail_url: String?
)