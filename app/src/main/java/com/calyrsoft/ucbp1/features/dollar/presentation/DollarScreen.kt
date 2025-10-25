package com.calyrsoft.ucbp1.features.dollar.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DollarScreen(
    navController: NavController,
    viewModelDollar: DollarViewModel = koinViewModel()
) {
    val state by viewModelDollar.uiState.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            when (val stateValue = state) {
                is DollarViewModel.DollarUIState.Error -> {
                    Text(
                        text = stateValue.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }

                DollarViewModel.DollarUIState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(32.dp)
                    )
                }

                is DollarViewModel.DollarUIState.Success -> {
                    // Card con valores actuales
                    CurrentDollarCard(dollar = stateValue.data)

                    Spacer(modifier = Modifier.height(24.dp))

                    // Historial
                    Text(
                        text = "Historial de Cambios",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    DollarHistoryList(history = stateValue.history)
                }
            }
        }
    }
}

@Composable
fun CurrentDollarCard(dollar: DollarModel) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Valor Actual del Dólar",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dólar Oficial
            Text(
                text = "Dólar Oficial",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DollarValueItem("Compra", dollar.dollarOfficialCompra ?: "N/A", MaterialTheme.colorScheme.primary)
                DollarValueItem("Venta", dollar.dollarOfficialVenta ?: "N/A", MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            // Dólar Paralelo
            Text(
                text = "Dólar Paralelo",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DollarValueItem("Compra", dollar.dollarParallelCompra ?: "N/A", MaterialTheme.colorScheme.secondary)
                DollarValueItem("Venta", dollar.dollarParallelVenta ?: "N/A", MaterialTheme.colorScheme.secondary)
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Actualizado: ${getCurrentTime()}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun DollarValueItem(title: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = color.copy(alpha = 0.8f)
        )
        Text(
            text = "Bs $value",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
fun DollarHistoryList(history: List<DollarModel>) {
    if (history.isEmpty()) {
        Text(
            text = "No hay historial disponible",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(16.dp)
        )
    } else {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(history) { dollar ->
                DollarHistoryItem(dollar)
            }
        }
    }
}

@Composable
fun DollarHistoryItem(dollar: DollarModel) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = formatDate(dollar.timestamp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Oficial", fontWeight = FontWeight.Bold)
                    Text("Compra: ${dollar.dollarOfficialCompra ?: "--"}")
                    Text("Venta: ${dollar.dollarOfficialVenta ?: "--"}")
                }
                Column {
                    Text("Paralelo", fontWeight = FontWeight.Bold)
                    Text("Compra: ${dollar.dollarParallelCompra ?: "--"}")
                    Text("Venta: ${dollar.dollarParallelVenta ?: "--"}")
                }
            }
        }
    }
}

// Utilidades
private fun getCurrentTime(): String =
    SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

private fun formatDate(timestamp: Long): String =
    try {
        SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date(timestamp))
    } catch (e: Exception) {
        "Fecha inválida"
    }
