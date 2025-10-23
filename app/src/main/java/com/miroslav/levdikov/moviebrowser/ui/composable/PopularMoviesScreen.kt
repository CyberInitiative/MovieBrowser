package com.miroslav.levdikov.moviebrowser.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.miroslav.levdikov.moviebrowser.data.model.MovieModel
import com.miroslav.levdikov.moviebrowser.ui.viewmodel.MovieViewModel

@Composable
fun PopularMoviesScreen(
    viewModel: MovieViewModel,
    modifier: Modifier = Modifier,
    onNavigateToDetails: (Int) -> Unit,
) {
    val movies by viewModel.moviesState.collectAsStateWithLifecycle()
    val isMoviesLoading by viewModel.moviesLoadingState.collectAsStateWithLifecycle()

    PopularMoviesContent(
        movieModels = movies,
        isMoviesLoading = isMoviesLoading,
        modifier = modifier,
        onAddToFavorites = viewModel::setFavoriteStatus,
        onNavigateToDetails = onNavigateToDetails,
    )
}

@Composable
private fun PopularMoviesContent(
    movieModels: List<MovieModel>,
    isMoviesLoading: Boolean,
    modifier: Modifier = Modifier,
    onAddToFavorites: (MovieModel) -> Unit,
    onNavigateToDetails: (Int) -> Unit,
) {
    if (!isMoviesLoading) {
        MoviesList(
            movieModels = movieModels,
            modifier = modifier
                .padding(horizontal = 10.dp)
                .padding(vertical = 15.dp),
            onFavoriteButtonClick = onAddToFavorites,
            onNavigateToDetails = onNavigateToDetails,
        )
    } else {
        ListLoadingIndicator(modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun ListLoadingIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(35.dp),
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Loading",
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}