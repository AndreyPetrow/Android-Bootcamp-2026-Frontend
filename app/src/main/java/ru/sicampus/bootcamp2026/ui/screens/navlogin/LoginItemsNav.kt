package ru.sicampus.bootcamp2026.ui.screens.navlogin

import ru.sicampus.bootcamp2026.R
import ru.sicampus.bootcamp2026.ui.navigation.BottomNavItem

object LoginItemsNav {
    val NavItems = listOf(
        LoginNavItem(
            name = "login",
            route = "login_screen"
        ),
        LoginNavItem(
            name = "signup",
            route = "sign_up_screen"
        )
    )

}