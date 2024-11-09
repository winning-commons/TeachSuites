package com.example.teachsuite_android.com.example.teachsuite_android

import com.google.gson.annotations.SerializedName

data class GradeResponse(
    @SerializedName("points") val points: String,
    @SerializedName("feedback") val feedback: String,
    @SerializedName("improvements") val improvements: String,
    @SerializedName("sources") val sources: String
)
