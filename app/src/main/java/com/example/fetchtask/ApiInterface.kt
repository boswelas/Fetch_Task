package com.example.fetchtask

import com.example.fetchtask.models.FetchItems
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/hiring.json")
    suspend fun getAllItems():Response<FetchItems>
}