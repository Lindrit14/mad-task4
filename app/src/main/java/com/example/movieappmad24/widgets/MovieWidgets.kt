package com.example.movieappmad24.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MoviesViewModel

@Composable
fun MovieList(modifier: Modifier, movies: List<Movie>, navController: NavController, moviesViewModel: MoviesViewModel) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(movies) { movie ->
            MovieRow(
                movie = movie,
                onMovieRowClick = { movieId -> navController.navigate("detail/$movieId") },
                onFavClick = { moviesViewModel.toggleFavorite(movie) }
            )
        }
    }
}

@Composable
fun MovieRow(movie: Movie, onMovieRowClick: (String) -> Unit, onFavClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(25.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { onMovieRowClick(movie.id) }
    ) {
        Box {
            AsyncImage(
                model = movie.images.firstOrNull() ?: "",
                contentDescription = "Movie image",
                modifier = Modifier.height(150.dp).fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            IconButton(onClick = onFavClick, modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(
                    tint = Color.Red,
                    imageVector = if (movie.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Toggle Favorite"
                )
            }
        }
        var arrow by remember { mutableStateOf(false) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = movie.title, fontSize = 20.sp)
            IconButton(onClick = { arrow = !arrow }) {
                Icon(
                    imageVector = if (arrow) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand Details"
                )
            }
        }
        AnimatedVisibility(visible = arrow) {
            Column(modifier = Modifier.padding(12.dp)) {
                MovieDetails(movie = movie)
            }
        }
    }
}

@Composable
fun MovieDetails(movie: Movie) {
    Text("Director: ${movie.director}")
    Text("Actors: ${movie.actors}")
    Text("Genre: ${movie.genre}")
    Text("Release Year: ${movie.year}")
    Text("Rating: ${movie.rating}")
    Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(vertical = 12.dp))
    Text("Plot: ${movie.plot}")
}

