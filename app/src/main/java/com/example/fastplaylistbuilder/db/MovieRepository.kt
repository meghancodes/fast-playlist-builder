package com.example.fastplaylistbuilder.db

import android.app.Application
import androidx.annotation.WorkerThread
import com.example.fastplaylistbuilder.api.Movie
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MovieRepository(app: Application) {
    private var movieDao: MovieDao
    private lateinit var playlistMovies: List<MovieEntity>

    private val database = MovieDatabase.getDatabase(app)

    init {
        movieDao = database.movieDao()
        runBlocking {
            launch {
                playlistMovies = movieDao.getAll()
            }
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(movie: MovieEntity) {
        movieDao.insert(movie)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPlaylistMovies(): List<MovieEntity> {
        return playlistMovies
    }

    // convert an "api" movie object into a db movie object for easy insert
    fun convertMovieToEntity(movie: Movie) =
        movie.title?.let { MovieEntity(it, movie.year, movie.rating, movie.poster) }

    // convert db movie objects into "api" movie objects
    fun convertEntitiesToMovies(movies: List<MovieEntity>): List<Movie> {
        val movieList: MutableList<Movie> = mutableListOf()
        for (entityMovie in movies) {
            val movie = Movie(entityMovie.year, entityMovie.rating, entityMovie.poster, entityMovie.title)
            movieList.add(movie)
        }

        return movieList
    }
}