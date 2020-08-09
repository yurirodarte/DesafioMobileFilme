package com.example.desafiokotlinmovies

import com.google.gson.annotations.SerializedName

data class GetFilmesResposta(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Filmes>,
    @SerializedName("total_pages") val pages: Int
)