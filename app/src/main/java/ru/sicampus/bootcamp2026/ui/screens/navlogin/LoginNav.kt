package ru.sicampus.bootcamp2026.ui.screens.navlogin

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.sicampus.bootcamp2026.ui.navigation.ItemsNav
import ru.sicampus.bootcamp2026.ui.screens.book.BookScreen
import ru.sicampus.bootcamp2026.ui.screens.incomingbooks.IncomingScreen
import ru.sicampus.bootcamp2026.ui.screens.login.LoginScreen
import ru.sicampus.bootcamp2026.ui.screens.profile.ProfileScreen
import ru.sicampus.bootcamp2026.ui.screens.schedule.ScheduleScreen
import ru.sicampus.bootcamp2026.ui.screens.signup.SignUpScreen

@Composable
fun LoginNavHost(
    navController: NavHostController = rememberNavController(),
    context: Context
) {
    NavHost(
        navController = navController,
        startDestination = LoginItemsNav.NavItems[0].route
    ) {
        composable(LoginItemsNav.NavItems[0].route) {
            LoginScreen(context = context, navHostController = navController)
        }

        composable(LoginItemsNav.NavItems[1].route) {
            SignUpScreen(navHostController =  navController, context = context)
        }

    }
}