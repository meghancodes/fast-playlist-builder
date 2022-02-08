package com.example.fastplaylistbuilder.api

import com.example.fastplaylistbuilder.api.Movie
import com.google.gson.annotations.SerializedName

class MovieResponse {
    @SerializedName("Search")
    var movieList: List<Movie>? = null
}