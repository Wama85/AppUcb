package com.calyrsoft.ucbp1.features.movie.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.calyrsoft.ucbp1.features.movie.domain.model.Movie
import com.calyrsoft.ucbp1.features.movie.presentation.components.MovieCard
import com.calyrsoft.ucbp1.features.movie.presentation.viewmodel.MoviesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoviesScreen(
    navController: NavController,
    navigateToDetail: (movie: Movie) -> Unit,
    viewModel: MoviesViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is MoviesViewModel.MoviesUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is MoviesViewModel.MoviesUiState.Success -> {
                LazyColumn(modifier = Modifier.padding(8.dp)) {
                    items(state.movies) { movie ->
                        MovieCard(
                            movie = movie,
                            onRatingChange = { rating ->
                                viewModel.updateMovieRating(movie.id, rating)
                            },
                            onWatchLaterClick = {
                                viewModel.toggleWatchLater(movie.id)
                            },
                            onDetailClick = {
                                navigateToDetail(movie)
                            }
                        )
                    }
                }
            }

            is MoviesViewModel.MoviesUiState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
