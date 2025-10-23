package com.miroslav.levdikov.moviebrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.miroslav.levdikov.moviebrowser.ui.composable.navigation.AppNavHost
import com.miroslav.levdikov.moviebrowser.ui.theme.MovieBrowserTheme
import com.miroslav.levdikov.moviebrowser.ui.viewmodel.MovieViewModel

class MainActivity : ComponentActivity() {
    private val movieViewModel: MovieViewModel by viewModels { MovieViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb()))

        setContent {
            MovieBrowserTheme {
                AppNavHost(
                    movieViewModel = movieViewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}