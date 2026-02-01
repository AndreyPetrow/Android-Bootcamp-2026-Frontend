package ru.sicampus.bootcamp2026

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.sicampus.bootcamp2026.book.BookScreen
import ru.sicampus.bootcamp2026.incomingbooks.IncomingScreen
import ru.sicampus.bootcamp2026.navigation.ItemsNav
import ru.sicampus.bootcamp2026.profile.ProfileScreen
import ru.sicampus.bootcamp2026.schedule.ScheduleScreen
import ru.sicampus.bootcamp2026.ui.theme.AndroidBootcamp2026FrontendTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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
                NavHostContainer(navController, it)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {

    NavHost(
        navController = navController,
        startDestination = ItemsNav.BottomNavItems[0].route,
        modifier = Modifier.padding(paddingValues = padding),

        builder = {
            composable(ItemsNav.BottomNavItems[0].route) {
                ScheduleScreen(navHostController = navController)
            }

            composable(ItemsNav.BottomNavItems[1].route) {
                IncomingScreen()
            }

            composable(ItemsNav.BottomNavItems[2].route) {
                ProfileScreen()
            }
            composable(ItemsNav.BottomNavItems[3].route) {
                BookScreen(navController)
            }
        })
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    NavigationBar(
        containerColor = Color.White) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        ItemsNav.BottomNavItems.subList(fromIndex = 0 ,toIndex = 3).forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)
                },
                icon = {
                    Icon(painterResource(navItem.icon), contentDescription = navItem.label)
                },
                label = {
                    Text(text = navItem.label)
                },
                alwaysShowLabel = true,

                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xff155DFC),
                    unselectedIconColor = Color(0xffB3C1DE),
                    selectedTextColor = Color(0xff155DFC),
                    unselectedTextColor = Color(0xffB3C1DE),
                    indicatorColor = Color.White
                )
            )
        }
    }
}