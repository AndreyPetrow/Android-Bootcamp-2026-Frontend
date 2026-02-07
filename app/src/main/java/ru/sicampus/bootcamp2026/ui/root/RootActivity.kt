package ru.sicampus.bootcamp2026.ui.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import ru.sicampus.bootcamp2026.ui.screens.AppNavHost
import ru.sicampus.bootcamp2026.ui.screens.BottomNavigationBar

class RootActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(navController)
                }
            ){
                AppNavHost(navController, it)
            }
        }
    }
}