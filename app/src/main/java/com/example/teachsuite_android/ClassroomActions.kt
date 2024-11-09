// ClassroomActions.kt
package com.example.teachsuite_android.com.example.teachsuite_android


import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun fetchClassrooms(): List<ApiResponse> {
    val teacherId = "91234790-8f47-11e9-bc42-526af7764f64"
    val requestBody = ClassroomRequest(teacherId = teacherId)
    Log.i("ClassroomActions", "Preparing request with teacher ID: $teacherId")

    return withContext(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.getClasses(requestBody)
            if (response.isSuccessful) {
                val classrooms = response.body() ?: emptyList()

                // Log values from the first element if the list is not empty
                if (classrooms.isNotEmpty()) {
                    val firstClassroom = classrooms[0]
                    Log.i("ClassroomActions", "First Classroom Details:")
                    Log.i("ClassroomActions", "ID: ${firstClassroom.id}")
                    Log.i("ClassroomActions", "Teacher ID: ${firstClassroom.teacherId}")
                    Log.i("ClassroomActions", "Name: ${firstClassroom.name}")
                    Log.i("ClassroomActions", "Google Classroom ID: ${firstClassroom.googleClassroomId}")
                    Log.i("ClassroomActions", "Description: ${firstClassroom.description}")
                    Log.i("ClassroomActions", "Is Active: ${firstClassroom.isActive}")
                    Log.i("ClassroomActions", "Created At: ${firstClassroom.createdAt}")
                    Log.i("ClassroomActions", "Updated At: ${firstClassroom.updatedAt}")
                } else {
                    Log.i("ClassroomActions", "No classrooms returned in the response.")
                }

                classrooms
            } else {
                Log.e("ClassroomActions", "Request failed with code: ${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("ClassroomActions", "Network request error: ${e.message}", e)
            emptyList()
        }
    }
}