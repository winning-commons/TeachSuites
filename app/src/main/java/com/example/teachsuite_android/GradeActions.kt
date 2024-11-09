package com.example.teachsuite_android.com.example.teachsuite_android

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun gradeSARequest(gradeRequest: GradeRequest): GradeResponse? {
    return withContext(Dispatchers.IO) {
        try {
            Log.e("GradeActions", "Input: ${gradeRequest.saInput}, ${gradeRequest.saQuestion}")
            val response = RetrofitInstance.api.gradeSA(gradeRequest)
            if (response.isSuccessful && response.body() != null) {
                val gradeResponse = response.body()!!
                Log.i("GradeActions", "Grading response: $gradeResponse")
                gradeResponse
            } else {
                Log.e("GradeActions", "Grading failed with code: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("GradeActions", "Error grading with AI: ${e.message}", e)
            null
        }
    }
}
