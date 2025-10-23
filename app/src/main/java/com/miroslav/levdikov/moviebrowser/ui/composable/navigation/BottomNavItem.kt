package com.miroslav.levdikov.moviebrowser.ui.composable.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItem(
    val label: String,
    val selectedIcon: ImageVector,
    val contentDescription: String,
    val route: NavRoute
) {
    POPULAR_MOVIES(
        label = "Popular",
        selectedIcon = Icons.Filled.Search,
        contentDescription = "Popular movies icon",
        route = NavRoute.PopularMovies,
    ),
    FAVORITES_MOVIES(
        label = "Favorites",
        selectedIcon = Icons.Filled.Star,
        contentDescription = "Favorite movies icon",
        route = NavRoute.Favorites
    ),
}