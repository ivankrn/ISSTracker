package ru.ivankrn.isstracker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItems (
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {
    data object MainScreenList : BottomNavigationItems(
        route = "main",
        title = "Главная",
        icon = Icons.Outlined.Home
    )
    data object DetailsScreen : BottomNavigationItems(
        route = "satellite_pass_details",
        title = "Подробнее",
        icon = Icons.Outlined.Info
    )
    data object FavoritesScreen : BottomNavigationItems(
        route = "favorites",
        title = "Избранные",
        icon = Icons.Outlined.Star
    )
    data object SettingsScreen : BottomNavigationItems(
        route = "settings",
        title = "Настройки",
        icon = Icons.Outlined.Settings
    )
}