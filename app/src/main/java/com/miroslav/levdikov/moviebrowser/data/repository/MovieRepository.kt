package com.miroslav.levdikov.moviebrowser.data.repository

import com.miroslav.levdikov.moviebrowser.data.model.MovieModel

interface MovieRepository {

    suspend fun getPopularMovies(language: String = "en", page: Int = 1): List<MovieModel>
}