package com.calyrsoft.ucbp1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.calyrsoft.ucbp1.features.dollar.presentation.DollarScreen
import com.calyrsoft.ucbp1.features.github.presentation.GithubScreen
import com.calyrsoft.ucbp1.features.login.presentation.LoginScreen
import com.calyrsoft.ucbp1.features.movie.presentation.screen.MovieDetailScreen
import com.calyrsoft.ucbp1.features.movie.presentation.screen.MoviesScreen
import com.calyrsoft.ucbp1.features.notification.presentation.NotificationScreen
import com.calyrsoft.ucbp1.features.profile.presentation.ProfileScreen
import com.calyrsoft.ucbp1.features.movie.domain.model.Movie
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.net.URLDecoder

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Profile.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        composable(Screen.Dollar.route) {
            DollarScreen(navController = navController)
        }

        composable(Screen.Github.route) {
            GithubScreen(navController = navController)
        }

        // ✅ Pantalla principal de películas
        composable(Screen.Movie.route) {
            MoviesScreen(
                navController = navController,
                navigateToDetail = { movie ->
                    val movieJson = Json.encodeToString(movie)
                    val encoded = URLEncoder.encode(movieJson, "UTF-8")
                    navController.navigate("${Screen.MovieDetail.route}/$encoded")
                }
            )
        }

        composable(Screen.Notification.route) {
            NotificationScreen(navController = navController)
        }

        // ✅ Pantalla detalle: recibe el objeto Movie serializado
        composable(
            route = "${Screen.MovieDetail.route}/{movie}",
            arguments = listOf(navArgument("movie") { type = NavType.StringType })
        ) { backStackEntry ->
            val movieJson = backStackEntry.arguments?.getString("movie") ?: ""
            val decoded = URLDecoder.decode(movieJson, "UTF-8")
            val movie = Json.decodeFromString<Movie>(decoded)

            MovieDetailScreen(
                movie = movie,
                navController = navController
            )
        }
    }
}
