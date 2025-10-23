package com.miroslav.levdikov.moviebrowser.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.miroslav.levdikov.moviebrowser.ui.viewmodel.MovieViewModel

@Composable
fun FavoritesScreen(
    viewModel: MovieViewModel,
    modifier: Modifier = Modifier,
    onNavigateToDetails: (Int) -> Unit,
) {
    val favorites by viewModel.favoriteMoviesState.collectAsStateWithLifecycle()

    if (favorites.isNotEmpty()) {
        MoviesList(
            movieModels = favorites,
            modifier = modifier
                .padding(horizontal = 10.dp)
                .padding(vertical = 15.dp),
            onFavoriteButtonClick = viewModel::setFavoriteStatus,
            onNavigateToDetails = onNavigateToDetails,
        )
    } else {
        EmptyFavorites(modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun EmptyFavorites(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Favorites list is empty.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall
        )
    }
}