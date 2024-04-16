package com.example.movieappmad24.models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class MoviesViewModel: ViewModel() {

    private val _movieList = getMovies().toMutableStateList()
    val movieList: List<Movie> get() = _movieList

    private val _favoriteMovieList = mutableStateListOf<Movie>()
    val favoriteMovieList: List<Movie> get() = _favoriteMovieList

    fun toggleFavorite(movie: Movie) {
        movie.isFavorite = !movie.isFavorite

        if (movie.isFavorite) {
            if (!_favoriteMovieList.contains(movie)) {
                _favoriteMovieList.add(movie)
            }
        } else {
            _favoriteMovieList.remove(movie)
        }
    }

    private fun updateLists() {
        _movieList.forEach { movie ->
            if (movie.isFavorite && !_favoriteMovieList.contains(movie)) {
                _favoriteMovieList.add(movie)
            } else if (!movie.isFavorite && _favoriteMovieList.contains(movie)) {
                _favoriteMovieList.remove(movie)
            }
        }
    }
}


