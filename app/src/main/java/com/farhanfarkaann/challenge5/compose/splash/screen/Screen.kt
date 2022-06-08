package com.farhanfarkaann.challenge5.compose.splash.screen


sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("home_screen")
}