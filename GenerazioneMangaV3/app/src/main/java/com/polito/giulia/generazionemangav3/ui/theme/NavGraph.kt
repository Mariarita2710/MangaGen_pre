package com.polito.giulia.generazionemangav3.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.polito.giulia.generazionemangav3.AppViewModel
import com.polito.giulia.generazionemangav3.HomePage
import com.polito.giulia.generazionemangav3.screens.DigitalScreen
import com.polito.giulia.generazionemangav3.screens.MangaDetail
import com.polito.giulia.generazionemangav3.screens.MangaPageScreen
import com.polito.giulia.generazionemangav3.screens.PaperScreen
import com.polito.giulia.generazionemangav3.screens.ProfileScreen
import com.polito.giulia.generazionemangav3.screens.SearchScreen
import com.polito.giulia.generazionemangav3.screens.VolumePageScreen


@Composable
fun NavGraph(navController: NavHostController, viewModel: AppViewModel){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomePage(viewModel, navController)
        }
        composable(route = Screen.Search.route) {
            SearchScreen(navController,viewModel)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController, viewModel)
        }
        // the following are just for Anna, pls don't touch them
        composable(route = Screen.Calendar.route) {
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
    }
}