package com.example.teachsuite_android.com.example.teachsuite_android

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://108.30.159.119:8443/"  // Replace with your actual base URL

    val api: ClassroomApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ClassroomApiService::class.java)
    }
}