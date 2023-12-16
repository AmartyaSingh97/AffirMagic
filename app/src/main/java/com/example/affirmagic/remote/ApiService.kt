package com.example.affirmagic.remote

import com.example.affirmagic.data.AffirmationsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("dailyzen/")
    suspend fun getAffirmations(@Query("date") date: String, @Query("version") version: Int): Response<List<AffirmationsModel>>

}