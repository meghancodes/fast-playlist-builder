package com.example.fastplaylistbuilder.adapters

import com.example.fastplaylistbuilder.R
import com.example.fastplaylistbuilder.api.Movie
import com.example.fastplaylistbuilder.databinding.ItemMovieBinding

class MovieAdapter(
    private val list: List<Movie>,
    private val movieListener: MovieListener
) : BaseAdapter<ItemMovieBinding, Movie>(list) {

    override val layoutId: Int = R.layout.item_movie

    override fun bind(binding: ItemMovieBinding, item: Movie) {
        binding.apply {
            movie = item
            listener = movieListener
            executePendingBindings()
        }
    }
}

interface MovieListener {
    fun onMovieClicked(movie: Movie)
}