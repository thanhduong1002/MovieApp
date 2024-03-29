package com.example.movieapp.retrofit

import com.example.movieapp.models.DetailMovieResponse
import com.example.movieapp.models.PopularResponse
import com.example.movieapp.models.VideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieInterface {
    @GET("popular")
    fun getPopularList(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Call<PopularResponse>

    @GET("top_rated")
    fun getTopRatedList(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Call<PopularResponse>

    @GET("upcoming")
    fun getUpcomingList(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Call<PopularResponse>

    @GET("{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String
    ): Call<DetailMovieResponse>

    @GET("{movie_id}/videos")
    fun getVideoById(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String
    ): Call<VideoResponse>
}