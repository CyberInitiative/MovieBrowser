package com.miroslav.levdikov.moviebrowser.ui.composable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.miroslav.levdikov.moviebrowser.data.model.MovieModel

@Composable
fun MoviesList(
    movieModels: List<MovieModel>,
    modifier: Modifier = Modifier,
    onFavoriteButtonClick: (MovieModel) -> Unit,
    onNavigateToDetails: (Int) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(items = movieModels, key = { _, movie -> movie.id }) { index, movie ->
            MovieItem(
                movieItem = movie,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .border(
                        width = 0.1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .background(MaterialTheme.colorScheme.surface),
                onFavoriteButtonClick = onFavoriteButtonClick,
                onNavigateToDetails = onNavigateToDetails,
            )
            if (index != movieModels.lastIndex) {
                Spacer(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(10.dp)
                )
            }
        }
    }
}

@Composable
fun MovieItem(
    movieItem: MovieModel,
    modifier: Modifier = Modifier,
    onFavoriteButtonClick: (MovieModel) -> Unit,
    onNavigateToDetails: (Int) -> Unit,
) {
    Row(
        modifier = modifier
            .clickable { onNavigateToDetails(movieItem.id) }
    ) {
        MoviePosterWithFavoriteButton(
            movieModelItem = movieItem,
            modifier = Modifier.wrapContentSize(),
            onFavoriteButtonClick = onFavoriteButtonClick
        )
        MovieItemTitleAndOverview(
            movie = movieItem,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(horizontal = 10.dp)
                .padding(bottom = 5.dp),
        )
    }
}

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp,
) {
    IconButton(
        onClick = {
            Log.d("Click", "Favorite button is clicked")
            onClick()
        },
        modifier = modifier,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                modifier = Modifier.size(iconSize + 8.dp),
                tint = if (isFavorite) Color.Black else Color.White
            )

            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                modifier = Modifier.size(iconSize),
                tint = if (isFavorite) Color.Yellow else Color.Black
            )
        }
    }
}

@Composable
private fun MoviePosterWithFavoriteButton(
    movieModelItem: MovieModel,
    modifier: Modifier = Modifier,
    onFavoriteButtonClick: (MovieModel) -> Unit,
) {
    Box(modifier = modifier.wrapContentSize()) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/${movieModelItem.posterPath}",
            contentDescription = "Translated description of what the image contains",
            modifier = Modifier
                .aspectRatio(2f / 3f)
                .fillMaxHeight(),
            placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            error = ColorPainter(MaterialTheme.colorScheme.errorContainer),
        )

        FavoriteButton(
            isFavorite = movieModelItem.isFavorite,
            onClick = { onFavoriteButtonClick(movieModelItem) },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 1.5.dp, start = 1.5.dp),
            iconSize = 32.dp
        )
    }
}

@Composable
private fun MovieItemTitleAndOverview(
    movie: MovieModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // movie title
        Text(
            text = movie.title,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(10.dp))

        // movie overview
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = ParagraphStyle(
                        textAlign = TextAlign.Justify,
                        textIndent = TextIndent(firstLine = 16.sp)
                    )
                ) {
                    append(movie.overview)
                }
            },
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall
        )
    }
}