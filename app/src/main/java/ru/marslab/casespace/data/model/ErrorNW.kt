package ru.marslab.casespace.data.model


import com.google.gson.annotations.SerializedName

data class ErrorNW(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("service_version")
    val serviceVersion: String
)