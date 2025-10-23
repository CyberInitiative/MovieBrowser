package com.miroslav.levdikov.moviebrowser.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import com.miroslav.levdikov.moviebrowser.data.model.MovieModel
import com.miroslav.levdikov.moviebrowser.ui.viewmodel.MovieViewModel

@Composable
fun DetailsScreen(
    viewModel: MovieViewModel,
    movieId: Int,
    modifier: Modifier = Modifier,
) {
    var movieDetails by remember { mutableStateOf<MovieModel?>(null) }

    LaunchedEffect(Unit) {
        movieDetails = viewModel.getMovieById(movieId)
    }

    val onFavoriteButtonClick = { movie: MovieModel ->
        if (movieDetails != null) {
            movieDetails = movieDetails!!.copy(isFavorite = !movieDetails!!.isFavorite)
        }
        viewModel.setFavoriteStatus(movie)
    }

    DetailsScreenContent(
        movie = movieDetails,
        modifier = modifier,
        onFavoriteButtonClick = onFavoriteButtonClick,
    )
}

@Composable
private fun DetailsScreenContent(
    movie: MovieModel?,
    modifier: Modifier = Modifier,
    onFavoriteButtonClick: (MovieModel) -> Unit,
) {
    if (movie != null) {
        MovieDetails(
            movie = movie,
            modifier = modifier,
            onFavoriteButtonClick = onFavoriteButtonClick
        )
    } else {
        NoMovieDetails(modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun MovieDetails(
    movie: MovieModel,
    modifier: Modifier = Modifier,
    onFavoriteButtonClick: (MovieModel) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(vertical = 15.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        PosterAndFavoriteButton(
            movie = movie,
            modifier = Modifier.fillMaxSize(),
            onFavoriteButtonClick = onFavoriteButtonClick
        )

        Spacer(modifier = Modifier.height(15.dp))
        // movie title
        Text(
            text = movie.title,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(7.dp))
        // movie release year
        Text(
            text = movie.releaseDate.split("-")[0],
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(20.dp))
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
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 30.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun PosterAndFavoriteButton(
    movie: MovieModel,
    modifier: Modifier = Modifier,
    onFavoriteButtonClick: (MovieModel) -> Unit,
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (imageRef, buttonRef) = createRefs()

        AsyncImage(
            model = movie.posterPath,
            contentDescription = "Poster for ${movie.title} movie",
            modifier = Modifier
                .width(225.dp)
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(16.dp))
                .constrainAs(imageRef) {
                    centerTo(parent)
                },
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            error = ColorPainter(MaterialTheme.colorScheme.errorContainer),
        )

        FavoriteButton(
            isFavorite = movie.isFavorite,
            onClick = { onFavoriteButtonClick(movie) },
            modifier = Modifier
                .constrainAs(buttonRef) {
                    start.linkTo(imageRef.end, margin = 5.dp)
                    top.linkTo(imageRef.top)
                },
            iconSize = 32.dp
        )
    }
}

@Composable
private fun NoMovieDetails(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = "No details for this movie.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall
        )
    }
}