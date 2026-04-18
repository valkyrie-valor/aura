package com.aura.installer.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CloudDownload
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.aura.installer.ui.screens.browse.BrowseScreen
import com.aura.installer.ui.screens.detail.DetailScreen
import com.aura.installer.ui.screens.installed.InstalledScreen
import com.aura.installer.ui.screens.local.LocalScreen
import com.aura.installer.ui.screens.settings.SettingsScreen
import kotlinx.serialization.Serializable

sealed interface AuraDestination {
    @Serializable data object Browse : AuraDestination
    @Serializable data object Local : AuraDestination
    @Serializable data object Installed : AuraDestination
    @Serializable data object Settings : AuraDestination
    @Serializable data class Detail(
        val downloadUrl: String,
        val fileName: String,
    ) : AuraDestination
}

data class TopLevelDestination(
    val route: AuraDestination,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String,
)

val topLevelDestinations = listOf(
    TopLevelDestination(
        route = AuraDestination.Browse,
        selectedIcon = Icons.Filled.CloudDownload,
        unselectedIcon = Icons.Outlined.CloudDownload,
        label = "Browse",
    ),
    TopLevelDestination(
        route = AuraDestination.Local,
        selectedIcon = Icons.Filled.Folder,
        unselectedIcon = Icons.Outlined.Folder,
        label = "Local",
    ),
    TopLevelDestination(
        route = AuraDestination.Installed,
        selectedIcon = Icons.Filled.PhoneAndroid,
        unselectedIcon = Icons.Outlined.PhoneAndroid,
        label = "Installed",
    ),
    TopLevelDestination(
        route = AuraDestination.Settings,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        label = "Settings",
    ),
)

fun NavGraphBuilder.auraNavGraph(navController: NavController) {
    composable<AuraDestination.Browse> {
        BrowseScreen(
            onAssetClick = { downloadUrl, fileName ->
                navController.navigate(
                    AuraDestination.Detail(
                        downloadUrl = downloadUrl,
                        fileName = fileName,
                    )
                )
            }
        )
    }
    composable<AuraDestination.Local> { LocalScreen() }
    composable<AuraDestination.Installed> { InstalledScreen() }
    composable<AuraDestination.Settings> { SettingsScreen() }
    composable<AuraDestination.Detail> {
        DetailScreen(onBack = { navController.navigateUp() })
    }
}
