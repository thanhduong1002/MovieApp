package com.example.movieapp.retrofit

import com.example.movieapp.models.PopularResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieInterface {
    @GET("popular")
    fun getPopularList(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
    ): Call<PopularResponse>

    @GET("top_rated")
    fun getTopRatedList(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
    ): Call<PopularResponse>
}