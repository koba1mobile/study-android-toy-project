package com.example.toyproject.data

import com.google.gson.annotations.SerializedName

data class Owner (
    @SerializedName("avatar_url")
    val thumbnail_url: String?
)