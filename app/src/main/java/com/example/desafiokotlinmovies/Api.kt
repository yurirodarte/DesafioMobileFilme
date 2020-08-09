package com.example.desafiokotlinmovies

import retrofit2.*
import retrofit2.http.*

interface Api {

    @GET("movie/popular")
    fun getPopularFilmes(
        @Query("api_key") apiKey: String = "cabdc58ba8d9f9dd9924fafd5cbe584d",
        @Query("page") page: Int
    ): Call<GetFilmesResposta>
}