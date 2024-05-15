package com.polito.giulia.generazionemangav3.ui.theme

import SettingsScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.polito.giulia.generazionemangav3.AppViewModel
import com.polito.giulia.generazionemangav3.HomePage
import com.polito.giulia.generazionemangav3.screens.DigitalScreen
import com.polito.giulia.generazionemangav3.screens.Login
import com.polito.giulia.generazionemangav3.screens.MangaDetail
import com.polito.giulia.generazionemangav3.screens.MangaPageScreen
import com.polito.giulia.generazionemangav3.screens.MapScreen
import com.polito.giulia.generazionemangav3.screens.MyCalendar
import com.polito.giulia.generazionemangav3.screens.NotificationsScreen
import com.polito.giulia.generazionemangav3.screens.PaperScreen
import com.polito.giulia.generazionemangav3.screens.ProfileScreen
import com.polito.giulia.generazionemangav3.screens.ReviewScreen
import com.polito.giulia.generazionemangav3.screens.SearchScreen
import com.polito.giulia.generazionemangav3.screens.VolumePageScreen


@Composable
fun NavGraph(navController: NavHostController, viewModel: AppViewModel){
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            Login(viewModel, navController)
        }
        composable(route = Screen.Home.route) {
            HomePage(viewModel, navController)
        }
        composable(route = Screen.Search.route) {
            SearchScreen(navController,viewModel)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController, viewModel)
        }
        composable(route = Screen.Map.route) {
            MapScreen(viewModel,navController)
        }
        // the following are just for Anna, pls don't touch them
        composable(route = Screen.MangaPage.route) {
            MangaPageScreen(viewModel)
        }
        composable(route = "manga") {
            MangaDetail(viewModel,navController)
        }
        composable(route = "volume_page_screen/{number}", arguments = listOf(navArgument("number"){
            type = NavType.IntType
        })) {
            val numb = it.arguments?.getInt("number") ?: ""
            VolumePageScreen(viewModel, numb as Int)
        }
        composable(route = Screen.Paper.route) {
            PaperScreen(viewModel,navController)
        }
        composable(route = Screen.Digital.route) {
            DigitalScreen(viewModel,navController)
        }
        composable(route = Screen.Calendar.route) {
            MyCalendar(navController,viewModel)
        }
        composable(route = Screen.Review.route) {
            ReviewScreen(viewModel,navController)
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(viewModel,navController)
        }
         composable(route = Screen.Notifications.route) {
             NotificationsScreen(viewModel,navController)
        }
    }
}