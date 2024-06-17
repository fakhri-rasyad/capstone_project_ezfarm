package com.c241ps093.ezfarm.data.entity

import com.google.gson.annotations.SerializedName

data class SimpleResponse (
    @field:SerializedName("error")
    val error: Boolean = true,

    @field:SerializedName("message")
    val message: String? = null
)
