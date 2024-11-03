package ru.ivankrn.isstracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

import ru.ivankrn.isstracker.data.util.BadgeContextCache
import ru.ivankrn.isstracker.ui.navigation.BottomNavigationItems
import ru.ivankrn.isstracker.ui.screens.FavoriteSatellitePassesListScreen
import ru.ivankrn.isstracker.ui.screens.SatellitePassDetailsScreen
import ru.ivankrn.isstracker.ui.screens.SatellitePassesListScreen
import ru.ivankrn.isstracker.ui.screens.SettingsScreen
import ru.ivankrn.isstracker.ui.theme.ISSTrackerTheme
import ru.ivankrn.isstracker.viewModel.SatellitePassViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ISSTrackerTheme {
                ApplicationScreen()
            }
        }
    }
}

@Preview
@Composable
fun ApplicationScreen() {
    val navController = rememberNavController()
    AppNavigation(navController)
}

@Composable
fun AppNavigation(navigationController: NavHostController) {
    val viewModel = koinViewModel<SatellitePassViewModel>()
    val badgeContextCache = koinInject<BadgeContextCache>()
    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navigationController,
                badgeContextCache = badgeContextCache,
                modifier = Modifier
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            NavigationGraph(
                navController = navigationController,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, viewModel: SatellitePassViewModel) {
    NavHost(navController, startDestination = BottomNavigationItems.MainScreenList.route) {
        composable(BottomNavigationItems.MainScreenList.route) {
            SatellitePassesListScreen(
                viewModel,
                onNavigateToDetails = {
                    navController.navigate(
                        route = BottomNavigationItems.DetailsScreen.route
                    )
                }
            )
        }
        composable(BottomNavigationItems.DetailsScreen.route) {
            SatellitePassDetailsScreen(
                viewModel.selectedSatellitePass.value!!
            )
        }
        composable(BottomNavigationItems.FavoritesScreen.route) {
            FavoriteSatellitePassesListScreen(
                viewModel
            )
        }
        composable(BottomNavigationItems.SettingsScreen.route) {
            SettingsScreen(
                viewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(
    navController: NavHostController,
    badgeContextCache: BadgeContextCache,
    modifier: Modifier = Modifier
) {
    val screens = listOf(
        BottomNavigationItems.MainScreenList,
        BottomNavigationItems.DetailsScreen,
        BottomNavigationItems.FavoritesScreen,
        BottomNavigationItems.SettingsScreen
    )

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            NavigationBarItem(
                label = {
                    Text(text = screen.title)
                },
                icon = {
                    if (screen != BottomNavigationItems.SettingsScreen) {
                        Icon(imageVector = screen.icon!!, contentDescription = "")
                    } else {
                        if (badgeContextCache.isSettingsModified()) {
                            BadgedBox(
                                badge = {
                                    Badge()
                                }
                            ) {
                                Icon(imageVector = screen.icon!!, contentDescription = "")
                            }
                        } else {
                            Icon(imageVector = screen.icon!!, contentDescription = "")
                        }
                    }
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary
                ),
            )
        }
    }
}
