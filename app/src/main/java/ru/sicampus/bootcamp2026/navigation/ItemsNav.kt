package ru.sicampus.bootcamp2026.navigation


import ru.sicampus.bootcamp2026.R

object ItemsNav {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Расписание",
            icon = R.drawable.schedule,
            route = "schedule"
        ),
        BottomNavItem(
            label = "Входящие",
            icon = R.drawable.image,
            route = "incoming"
        ),
        BottomNavItem(
            label = "Профиль",
            icon = R.drawable.profile,
            route = "profile"
        ),
        BottomNavItem(
            label = "book",
            route = "booking"
        )
    )
}