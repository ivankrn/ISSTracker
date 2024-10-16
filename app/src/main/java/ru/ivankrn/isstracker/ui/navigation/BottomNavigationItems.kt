package ru.ivankrn.isstracker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItems (
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    data object MainScreenList : BottomNavigationItems(
        route = "main",
        title = "Главная",
        icon = Icons.Outlined.Home
    )
    data object DetailsScreen : BottomNavigationItems(
        route = "satellite_pass_details",
        title = "Подробности",
        icon = Icons.Outlined.Info
    )
//    Здесь потом будет экран с настройками (как раз выбор локации можно будет туда запихать)
//    data object SettingsScreen : BottomNavigationItems(
//        route = "settings",
//        title = "Settings screen",
//        icon = Icons.Outlined.Settings
//    )
}