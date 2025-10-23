package com.miroslav.levdikov.moviebrowser.data.repository

import com.miroslav.levdikov.moviebrowser.data.api.MovieService
import com.miroslav.levdikov.moviebrowser.data.model.MovieModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val movieService: MovieService,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MovieRepository {

    override suspend fun getPopularMovies(language: String, page: Int): List<MovieModel> {
        return withContext(coroutineDispatcher) {
            try {
                movieService.getPopularMovies(
                    language = language,
                    page = page,
                ).results.map { movieDto -> movieDto.mapToModel() }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
}