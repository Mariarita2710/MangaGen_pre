package com.polito.giulia.generazionemangav3.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screen(val route: String,
                    val title: String,
                    val icon: ImageVector
){
    object Home: Screen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    object Login: Screen(
        route = "login",
        title = "Login",
        icon = Icons.Default.Lock
    )
    object Calendar: Screen(
        route = "calendar",
        title = "Calendar",
        icon = Icons.Default.DateRange
    )
    object Search: Screen(
        route = "search",
        title = "Search",
        icon = Icons.Default.Search
    )
    object Map: Screen(
        route = "map",
        title = "Map",
        icon = Icons.Default.LocationOn
    )
    object Profile: Screen(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.AccountCircle
    )
    object Notifications: Screen(
        route = "notifications",
        title = "Notifications",
        icon = Icons.Default.Notifications
    )
    object Settings: Screen(
        route = "settings",
        title = "Settings",
        icon = Icons.Default.Settings
    )
    object MangaPage: Screen(
        route = "manga screen",
        title = "Manga screen",
        icon = Icons.Default.Star
    )
    object MangaDetail: Screen(
    route = "manga",
    title = "Manga",
    icon = Icons.Default.Star
    )
    object Volume: Screen(
        route = "volume",
        title = "Volume",
        icon = Icons.Default.Star
    )
    object Paper: Screen(
        route = "paper",
        title = "Paper",
        icon = Icons.Default.Email
    )
    object Digital: Screen(
        route = "digital",
        title = "Digital",
        icon = Icons.Default.Info
    )
    object Review: Screen(
        route = "review",
        title = "Review",
        icon = Icons.Default.Edit
    )
}
