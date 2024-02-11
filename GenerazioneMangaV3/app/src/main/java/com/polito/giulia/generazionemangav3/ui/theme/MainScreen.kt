package com.polito.giulia.generazionemangav3.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.polito.giulia.generazionemangav3.AppViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: AppViewModel){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        topBar = {
                if(currentDestination?.route == Screen.Settings.route
                    || currentDestination?.route == Screen.Notifications.route
                    ){
                    SmallTopAppBar(navController = navController)
                } else {
                    TopBar(navController = navController)
                }

        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        NavGraph(navController = navController,viewModel)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
val screens = listOf(
    Screen.Home,
    Screen.Calendar,
    Screen.Search,
    Screen.Map,
    Screen.Profile
)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.onBackground
    ) {
        screens.forEach{
            screen ->
            TopItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.TopItem(screen: Screen, currentDestination: NavDestination?, navController: NavHostController) {
        NavigationBarItem(
            label = {
                Text(text = screen.title)
            },
            icon = {
                Icon(
                    imageVector = screen.icon,
                    contentDescription = "Nav Icon"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                unselectedIconColor = MaterialTheme.colorScheme.tertiary
            ),
            selected = currentDestination?.hierarchy?.any() {
                it.route == screen.route
            } == true,
            onClick = {
                navController.navigate(screen.route)
            }
        )
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopItem2(navController: NavHostController) {
TopAppBar(
    title = {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(text = "MANGA GEN", fontFamily = fontFamily, fontWeight = FontWeight.SemiBold, fontSize = 40.sp, color = Color.White)
        }
    },
    colors = TopAppBarDefaults.smallTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary
    ),
    navigationIcon = {
        IconButton(
            onClick = {
                navController.navigate(Screen.Notifications.route)
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            BadgedBox(
                badge = {
                Badge(modifier = Modifier.size(10.dp)){
                }
            }
            ) {
                Icon(
                    imageVector = Screen.Notifications.icon,
                    contentDescription = "Notifications"
                )
            }
        }
    },
    actions = {
        IconButton(onClick = {  navController.navigate(Screen.Settings.route) },
            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.tertiary)) {
            Icon(
                imageVector = Screen.Settings.icon,
                contentDescription = "Settings"
            )
        }
    }
)
}

@Composable
fun TopBar(navController: NavHostController) {
    val screens = listOf(
        Screen.Notifications,
        Screen.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primary) {
                screens.forEach { screen ->
                    TopItem2(
                        navController = navController
                    )
                }

    }
}

@Composable
fun SmallTopAppBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(containerColor = MaterialTheme.colorScheme.onPrimaryContainer) {
        TopItem3(
            navController = navController,
            currentDestination = currentDestination
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopItem3(navController: NavHostController, currentDestination: NavDestination?) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                when(currentDestination?.route){
                    Screen.Notifications.route -> Text(text = "Notifications")
                    Screen.Settings.route -> Text(text = "Settings")
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigateUp()
                }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            if(currentDestination?.route !== Screen.Settings.route){
                IconButton(
                    onClick = {
                        navController.navigate(Screen.Settings.route)
                    }
                ) {
                    Icon(
                        imageVector = Screen.Settings.icon,
                        contentDescription = "Settings"
                    )
                }
            }
        }
    )
}
