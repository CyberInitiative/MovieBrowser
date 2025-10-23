package com.miroslav.levdikov.moviebrowser.ui.composable.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.miroslav.levdikov.moviebrowser.ui.composable.DetailsScreen
import com.miroslav.levdikov.moviebrowser.ui.composable.FavoritesScreen
import com.miroslav.levdikov.moviebrowser.ui.composable.PopularMoviesScreen
import com.miroslav.levdikov.moviebrowser.ui.viewmodel.MovieViewModel

@Composable
fun AppNavHost(
    movieViewModel: MovieViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    val shouldShowAppBar = currentDestination?.hasRoute(NavRoute.Details::class) == false

    val navigateToDetails: (Int) -> Unit = { movieId ->
        navController.navigate(NavRoute.Details(movieId = movieId))
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (shouldShowAppBar) {
                BottomNavigation(
                    navController = navController,
                    currentDestination = currentDestination,
                )
            }
        }
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoute.PopularMovies,
            modifier = modifier.padding(contentPadding)
        ) {
            composable<NavRoute.PopularMovies> {
                PopularMoviesScreen(
                    viewModel = movieViewModel,
                    modifier = Modifier.fillMaxSize(),
                    onNavigateToDetails = navigateToDetails,
                )
            }

            composable<NavRoute.Favorites> {
                FavoritesScreen(
                    viewModel = movieViewModel,
                    modifier = Modifier.fillMaxSize(),
                    onNavigateToDetails = navigateToDetails,
                )
            }

            composable<NavRoute.Details> { backStackEntry ->
                val details: NavRoute.Details = backStackEntry.toRoute()

                DetailsScreen(
                    viewModel = movieViewModel,
                    movieId = details.movieId,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun BottomNavigation(
    navController: NavController,
    currentDestination: NavDestination?,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        BottomNavItem.entries.forEach { destination ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(destination.route::class)
            } ?: false

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(destination.route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        if (isSelected) destination.selectedIcon else destination.unselectedIcon,
                        contentDescription = destination.contentDescription,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.surface,
                    selectedIconColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                label = { Text(text = destination.label) }
            )
        }
    }
}