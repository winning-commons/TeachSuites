package com.example.teachsuite_android.com.example.teachsuite_android

//package com.example.teachsuite_android.network

import com.example.teachsuite_android.Classroom
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ClassroomApiService {

    @POST("api/get-classes")
    suspend fun getClasses(@Body requestBody: ClassroomRequest): Response<List<ApiResponse>>

    @POST("api/get-student-classes")
    suspend fun getStudentClasses(@Body requestBody: StudentRequest): Response<List<Classroom>>

    @POST("api/grade-sa")
    suspend fun gradeSA(@Body gradeRequest: GradeRequest): Response<GradeResponse>
}