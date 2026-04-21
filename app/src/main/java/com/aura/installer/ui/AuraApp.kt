package com.aura.installer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aura.installer.ui.navigation.AuraDestination
import com.aura.installer.ui.navigation.auraNavGraph
import com.aura.installer.ui.navigation.topLevelDestinations
import com.aura.installer.ui.screens.setup.SetupScreen
import com.aura.installer.ui.screens.setup.SetupViewModel

@Composable
fun AuraApp(setupViewModel: SetupViewModel = hiltViewModel()) {
    val isSetupComplete by setupViewModel.isSetupComplete.collectAsState()

    when (isSetupComplete) {
        null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        false -> SetupScreen(onSave = setupViewModel::saveSetup)
        true -> MainApp()
    }
}

@Composable
private fun MainApp() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            topLevelDestinations.forEach { destination ->
                val selected = currentDestination?.hierarchy?.any {
                    it.hasRoute(destination.route::class)
                } == true
                item(
                    selected = selected,
                    onClick = {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                            contentDescription = null,
                        )
                    },
                    label = { Text(destination.label) }
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = AuraDestination.Browse,
        ) {
            auraNavGraph(navController)
        }
    }
}
