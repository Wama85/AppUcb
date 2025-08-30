package com.calyrsoft.ucbp1.navigation

sealed class Screen(val route: String) {
   object Login : Screen("login")
   object Profile : Screen("profile")
   object Dollar: Screen("dollar")
}