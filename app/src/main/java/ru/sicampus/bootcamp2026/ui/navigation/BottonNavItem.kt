package ru.sicampus.bootcamp2026.ui.navigation

import androidx.compose.ui.graphics.painter.Painter
import ru.sicampus.bootcamp2026.R


data class BottomNavItem(
val label: String,
val icon: Int = R.drawable.profile,
val route:String,
)