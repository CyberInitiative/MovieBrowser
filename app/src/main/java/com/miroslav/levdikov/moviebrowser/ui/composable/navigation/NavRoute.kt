package com.miroslav.levdikov.moviebrowser.ui.composable.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoute {
    @Serializable
    data class Details(val movieId: Int) : NavRoute()

    @Serializable
    object PopularMovies : NavRoute()

    @Serializable
    object Favorites : NavRoute()
}