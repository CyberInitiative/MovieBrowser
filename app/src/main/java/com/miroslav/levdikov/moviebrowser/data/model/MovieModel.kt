package com.miroslav.levdikov.moviebrowser.data.model

data class MovieModel(
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val releaseDate: String,
    var isFavorite: Boolean = false,
)