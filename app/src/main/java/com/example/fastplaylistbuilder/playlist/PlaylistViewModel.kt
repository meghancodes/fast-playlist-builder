package com.example.fastplaylistbuilder.playlist

import android.app.Application
import androidx.lifecycle.*
import com.example.fastplaylistbuilder.adapters.MovieListener
import com.example.fastplaylistbuilder.api.Movie
import com.example.fastplaylistbuilder.db.MovieEntity
import com.example.fastplaylistbuilder.db.MovieRepository
import kotlinx.coroutines.launch

class PlaylistViewModel(app: Application) : AndroidViewModel(app), MovieListener {
    private val repository = MovieRepository(app)

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> = _movieList

    private val _emptyPlaylist = MutableLiveData<Boolean>()
    val emptyPlaylist: LiveData<Boolean> = _emptyPlaylist

    fun populatePlaylist() {
        var movieEntities: List<MovieEntity> = listOf()

        viewModelScope.launch {
            movieEntities = repository.getPlaylistMovies()
        }

        if (movieEntities.isNotEmpty()) {
            // convert entities to movies and add to movieList
            _movieList.value = repository.convertEntitiesToMovies(movieEntities)
        } else {
            _emptyPlaylist.value = true
        }
    }

    override fun onMovieClicked(movie: Movie) {
        /* no-op */
    }

}

class PlaylistViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaylistViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaylistViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}