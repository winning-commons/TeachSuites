package com.example.teachsuite_android.com.example.teachsuite_android

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiResponse(
    val id: String,

    @SerializedName("teacher_id")
    val teacherId: String,

    val name: String,

    @SerializedName("google_classroom_id")
    val googleClassroomId: String,

    val description: String,

    @SerializedName("is_active")
    val isActive: Boolean,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String
) : Parcelable