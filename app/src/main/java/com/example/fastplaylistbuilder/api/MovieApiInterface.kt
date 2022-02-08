package com.example.fastplaylistbuilder.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiInterface {
    @GET("?")
    fun getMovies(@Query("s") title: String, @Query("apiKey") apiKey: String) : Call<MovieResponse>

    companion object {

        var BASE_URL = "https://www.omdbapi.com/"

        fun create() : MovieApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(MovieApiInterface::class.java)

        }
    }
}