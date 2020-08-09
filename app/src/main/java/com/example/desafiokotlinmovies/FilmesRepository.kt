package com.example.desafiokotlinmovies

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FilmesRepository {
    private val api: Api

    init{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

            api = retrofit.create(Api::class.java)
    }

    fun getPopularFilmes(
        page: Int = 1,
        onSuccess: (movies: List<Filmes>) -> Unit,
        onError: () -> Unit
    ) {
        api.getPopularFilmes(page = page)
            .enqueue(object : Callback<GetFilmesResposta> {
                override fun onResponse(
                    call: Call<GetFilmesResposta>,
                    response: Response<GetFilmesResposta>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetFilmesResposta>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
}