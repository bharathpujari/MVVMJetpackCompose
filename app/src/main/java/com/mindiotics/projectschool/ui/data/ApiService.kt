package com.mindiotics.projectschool.ui.data

import retrofit2.http.GET

interface ApiService {
    @GET("s3k6-pzi2.json")
    suspend fun getHighSchools() : List<School>
}