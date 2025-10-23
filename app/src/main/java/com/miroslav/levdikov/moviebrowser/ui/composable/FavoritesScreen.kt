package com.miroslav.levdikov.moviebrowser.ui.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
        NoDataPlaceHolder(
            text = "Favorites list is empty.",
            modifier = Modifier.fillMaxSize(),
        )
    }
}