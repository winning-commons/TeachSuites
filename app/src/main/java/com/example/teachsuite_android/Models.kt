package com.example.teachsuite_android

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize // Add this annotation
data class Classroom(
    val teacherId: String,
    val name: String,
    val description: String,
    val googleClassId: String
) : Parcelable  // Add this interface