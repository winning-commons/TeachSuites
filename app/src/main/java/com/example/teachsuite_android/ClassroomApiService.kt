package com.example.teachsuite_android.com.example.teachsuite_android

//package com.example.teachsuite_android.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ClassroomApiService {
    @POST("api/get-classes")
    suspend fun getClasses(@Body requestBody: ClassroomRequest): Response<List<ApiResponse>>
}