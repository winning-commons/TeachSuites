package com.example.teachsuite_android.com.example.teachsuite_android

import android.util.Log
import com.example.teachsuite_android.Classroom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException

suspend fun fetchStudentClasses(studentId: String): List<Classroom> {
    val requestBody = StudentRequest(studentId)

    return withContext(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.getStudentClasses(requestBody)
            if (response.isSuccessful) {
                Log.e("StudentActions","response: ${response.body().toString()}")

                response.body() ?: emptyList()
            } else {
                Log.e("StudentActions", "Request failed with code: ${response.code()}")
                emptyList()
            }
        } catch (e: HttpException) {
            Log.e("StudentActions", "Network error: ${e.message()}", e)
            emptyList()
        } catch (e: Exception) {
            Log.e("StudentActions", "Error: ${e.message}", e)
            emptyList()
        }
    }
}