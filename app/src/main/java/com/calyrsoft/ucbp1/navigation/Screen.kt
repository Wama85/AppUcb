package com.calyrsoft.ucbp1.navigation

sealed class Screen(val route: String) {
   object Login : Screen("login")
   object Profile : Screen("profile")
   object Dollar: Screen("dollar")
   object Github: Screen("github")
   object Movie: Screen("movie")
   object  Notification: Screen("notification")
   data object MovieDetail : Screen("movie_detail/{movieId}") {
      fun createRoute(movieId: Int) = "movie_detail/$movieId"
   }
}