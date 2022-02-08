package com.example.fastplaylistbuilder.results

import android.app.Application
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.fastplaylistbuilder.results.AlertDialogServerAction.NoResults
import com.example.fastplaylistbuilder.results.AlertDialogServerAction.ServerFailure
import com.example.fastplaylistbuilder.results.AlertDialogServerAction.DbAddFailure
import com.example.fastplaylistbuilder.adapters.MovieListener
import com.example.fastplaylistbuilder.R
import com.example.fastplaylistbuilder.api.Movie
import com.example.fastplaylistbuilder.api.MovieApiInterface
import com.example.fastplaylistbuilder.api.MovieResponse
import com.example.fastplaylistbuilder.db.MovieEntity
import com.example.fastplaylistbuilder.db.MovieRepository
import kotlinx.coroutines.launch

class ResultsViewModel(app: Application): AndroidViewModel(app), MovieListener {
    private val repository = MovieRepository(app)
    private val resources: Resources = app.resources

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> = _movieList

    private val _movieClicked = MutableLiveData<String?>()
    val movieClicked: LiveData<String?> = _movieClicked

    private val _alertDialogAction = MutableLiveData<AlertDialogServerAction>()
    val alertDialogAction: LiveData<AlertDialogServerAction> = _alertDialogAction

    fun populateMovieList(searchQuery: String) {
        val apiInterface = MovieApiInterface.create().getMovies(searchQuery,
            resources.getString(R.string.app_string))

        apiInterface.enqueue( object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>?, response: Response<MovieResponse>?) {
                if (response?.body() != null) {
                    val movieResponse = response.body()
                    movieResponse?.let {
                        _movieList.value = movieResponse.movieList
                    }
                } else {
                    //response is null so show alert saying no results found
                    _alertDialogAction.value = NoResults
                }
            }

            override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {
                Log.d(TAG, "Server request failed. Error: $t")
                _alertDialogAction.value = ServerFailure
            }
        })
    }

    override fun onMovieClicked(movie: Movie) {
        val movieToInsert = movie.title?.let { repository.convertMovieToEntity(movie) }
        if (movieToInsert != null) {
            insertIntoDb(movieToInsert)
        } else {
            Log.d(TAG, "Failed to insert ${movie.title} into db")
            _alertDialogAction.value = DbAddFailure
        }

        _movieClicked.value = movie.title
    }

    fun insertIntoDb(movie: MovieEntity) = viewModelScope.launch {
        repository.insert(movie)
    }

    companion object {
        private const val TAG = "ResultsViewModel"
    }
}

class ResultsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResultsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}