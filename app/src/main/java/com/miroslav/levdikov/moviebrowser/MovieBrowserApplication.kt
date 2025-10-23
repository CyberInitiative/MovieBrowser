package com.miroslav.levdikov.moviebrowser

import android.app.Application
import com.miroslav.levdikov.moviebrowser.di.DependencyContainer

class MovieBrowserApplication : Application() {
    val dependencyContainer = DependencyContainer()
}