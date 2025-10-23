package com.miroslav.levdikov.moviebrowser.data.api

import com.miroslav.levdikov.moviebrowser.BuildConfig
import com.miroslav.levdikov.moviebrowser.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieService {
    @Headers(
        "Authorization: Bearer ${BuildConfig.TMDB_API_TOKEN}",
        "accept: application/json"
    )
    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int,
    ): MoviesResponse
}