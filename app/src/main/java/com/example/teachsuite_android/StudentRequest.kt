package com.example.teachsuite_android.com.example.teachsuite_android

import com.google.gson.annotations.SerializedName

data class StudentRequest(
    @SerializedName("student-id") val studentId: String
)