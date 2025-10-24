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

@Composable
fun AppNavigation(navController: NavHostController,modifier: Modifier = Modifier) {


    NavHost(
        navController = navController, // Usa el navController que recibes
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
        composable(Screen.Movie.route) {
            MoviesScreen(navController = navController)
        }
        composable(Screen.Notification.route) {
            NotificationScreen(navController = navController)
        }
        composable(
            route = Screen.MovieDetail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            MovieDetailScreen(
                movieId = movieId,
                navController = navController
            )
        }
    }
}