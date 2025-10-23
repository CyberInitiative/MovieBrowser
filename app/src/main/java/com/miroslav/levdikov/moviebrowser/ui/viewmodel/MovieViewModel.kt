package com.miroslav.levdikov.moviebrowser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.miroslav.levdikov.moviebrowser.MovieBrowserApplication
import com.miroslav.levdikov.moviebrowser.data.model.MovieModel
import com.miroslav.levdikov.moviebrowser.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private var _moviesState = MutableStateFlow<List<MovieModel>>(emptyList())
    val moviesState: StateFlow<List<MovieModel>> = _moviesState.asStateFlow()

    private var _favoriteMoviesState = MutableStateFlow<List<MovieModel>>(emptyList())
    val favoriteMoviesState: StateFlow<List<MovieModel>> = _favoriteMoviesState.asStateFlow()

    private var _moviesLoadingState = MutableStateFlow(false)
    val moviesLoadingState: StateFlow<Boolean> = _moviesLoadingState.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _moviesLoadingState.value = true
            _moviesState.value = movieRepository.getPopularMovies()
            _moviesLoadingState.value = false
        }
    }

    fun getMovieById(id: Int): MovieModel? {
        return _moviesState.value.find { it.id == id }
    }

    fun setFavoriteStatus(movie: MovieModel) {
        val currentFavorites = _favoriteMoviesState.value
        val isAlreadyFavorite = currentFavorites.any { it.id == movie.id }

        if (!isAlreadyFavorite) {
            addToFavorites(movie.copy(isFavorite = true))
        } else {
            removeFromFavorites(movie)
        }
    }

    private fun addToFavorites(movie: MovieModel) {
        _favoriteMoviesState.value += movie
        _moviesState.value = _moviesState.value.map {
            if (it.id == movie.id) it.copy(isFavorite = true) else it
        }
    }

    private fun removeFromFavorites(movie: MovieModel) {
        _favoriteMoviesState.value = _favoriteMoviesState.value.filter { it.id != movie.id }
        _moviesState.value = _moviesState.value.map {
            if (it.id == movie.id) it.copy(isFavorite = false) else it
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MovieBrowserApplication)
                    .dependencyContainer
                    .movieRepository

                MovieViewModel(movieRepository = myRepository)
            }
        }
    }
}