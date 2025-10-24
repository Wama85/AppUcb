package com.calyrsoft.ucbp1.features.movie.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.calyrsoft.ucbp1.features.movie.domain.model.Movie

@Composable
fun MovieCard(
    movie: Movie,
    onRatingChange: (Float) -> Unit,
    onDetailClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Imagen del poster
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
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = movie.overview ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Botón "Ver detalle"
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