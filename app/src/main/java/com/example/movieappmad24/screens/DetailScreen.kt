package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieappmad24.models.MoviesViewModel
import com.example.movieappmad24.widgets.*



@Composable
fun DetailScreen(
    movieId: String?,
    moviesViewModel: MoviesViewModel,
    navController: NavController
) {
    val movie = moviesViewModel.movieList.firstOrNull { it.id == movieId }

    if (movie != null) {
        Scaffold(
            topBar = {
                SimpleTopAppBar(title = movie.title) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                MovieRow(
                    movie = movie,
                    onMovieRowClick = { clickedMovieId ->
                        println("Clicked Movie ID: $clickedMovieId")
                    },
                    onFavClick = { moviesViewModel.toggleFavorite(movie) }
                )
                ExoplayerTrailer(uri = movie.trailer)
                LazyRow {
                    items(movie.images) { image ->
                        AsyncImage(model = image, contentDescription = null)
                    }
                }
            }
        }
    } else {
        Text("Movie not found")
    }
}



@Composable
fun ExoplayerTrailer(uri: String) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val videoURI = MediaItem.fromUri("android.resource://" + context.getPackageName() + "/${context.resources.getIdentifier(uri, "raw", context.packageName)}")
            setMediaItem(videoURI)
            prepare()
            playWhenReady = false
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.stop()
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { PlayerView(it).apply { player = exoPlayer } },
        modifier = Modifier.fillMaxWidth().height(200.dp)
    )
}