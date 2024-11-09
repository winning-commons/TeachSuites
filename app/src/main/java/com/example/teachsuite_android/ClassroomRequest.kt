package com.example.teachsuite_android.com.example.teachsuite_android

// ClassroomRequest.kt

import com.google.gson.annotations.SerializedName

data class ClassroomRequest(
    @SerializedName("teacher-id")
    val teacherId: String
)