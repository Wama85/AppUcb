package com.calyrsoft.ucbp1.features.movie.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.calyrsoft.ucbp1.features.movie.domain.model.Movie

@Composable
fun MovieCard(
    movie: Movie,
    onRatingChange: (Float) -> Unit,
    onWatchLaterClick: () -> Unit,
    onDetailClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = movie.posterPath,
                contentDescription = movie.title,
                modifier = Modifier
                    .width(80.dp)
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )
                    
                    IconButton(onClick = onWatchLaterClick) {
                        Icon(
                            imageVector = if (movie.watchLater) {
                                Icons.Filled.WatchLater
                            } else {
                                Icons.Outlined.WatchLater
                            },
                            contentDescription = "Ver más tarde",
                            tint = if (movie.watchLater) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                Color.Gray
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = movie.overview ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    Text(
                        text = "Calificación:",
                        style = MaterialTheme.typography.bodySmall
                    )
                    
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(5) { index ->
                            IconButton(
                                onClick = {
                                    val newRating = (index + 1).toFloat()
                                    onRatingChange(newRating)
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = if (index < movie.rating.toInt()) {
                                        Icons.Filled.Star
                                    } else {
                                        Icons.Outlined.Star
                                    },
                                    contentDescription = "Estrella ${index + 1}",
                                    tint = if (index < movie.rating.toInt()) {
                                        Color(0xFFFFD700)
                                    } else {
                                        Color.Gray
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = onDetailClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver detalle")
                }
            }
        }
    }
}
