package com.miroslav.levdikov.moviebrowser.di

import com.miroslav.levdikov.moviebrowser.data.api.MovieService
import com.miroslav.levdikov.moviebrowser.data.repository.MovieRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DependencyContainer {
    private val movieService = Retrofit.Builder()
        .baseUrl(TMDB_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieService::class.java)

    val movieRepository = MovieRepositoryImpl(movieService)

    companion object {
        private const val TMDB_BASE_URL = "https://api.themoviedb.org"
    }
}