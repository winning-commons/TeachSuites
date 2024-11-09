package com.example.teachsuite_android.com.example.teachsuite_android

import com.google.gson.annotations.SerializedName

data class GradeRequest(
    @SerializedName("sa-input") val saInput: String,
    @SerializedName("sa-question") val saQuestion: String
)