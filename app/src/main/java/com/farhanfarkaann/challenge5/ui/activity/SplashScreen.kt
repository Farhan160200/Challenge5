package com.farhanfarkaann.challenge5.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.farhanfarkaann.challenge5.MainActivity
import com.farhanfarkaann.challenge5.compose.splash.screen.SetupNavGraph
import com.farhanfarkaann.mycomposeapp.ui.theme.MyTheme

class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashTime: Long = 1000 // lama splashscreen berjalan

            setContent {
                MyTheme {
                    val navController = rememberNavController()
                    SetupNavGraph(navController = navController)
                }

                Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent) // Pindah ke Home Activity setelah  1 detik
            },splashTime)
        }
    }
}