package com.miroslav.levdikov.moviebrowser.ui.composable.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val contentDescription: String = "",
    val route: NavRoute
) {
    POPULAR_MOVIES(
        label = "Popular",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
        contentDescription = "Popular movies icon",
        route = NavRoute.PopularMovies,
    ),
    FAVORITES_MOVIES(
        label = "Favorites",
        selectedIcon = Icons.Filled.Star,
        unselectedIcon = Icons.Outlined.Star,
        contentDescription = "Favorite movies icon",
        route = NavRoute.Favorites
    ),
}