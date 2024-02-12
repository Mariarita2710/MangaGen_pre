package com.polito.giulia.generazionemangav3.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polito.giulia.generazionemangav3.AppViewModel
import com.polito.giulia.generazionemangav3.ui.theme.Green
import com.polito.giulia.generazionemangav3.ui.theme.Screen
import com.polito.giulia.generazionemangav3.ui.theme.Violet20
import com.polito.giulia.generazionemangav3.ui.theme.Violet40
import com.polito.giulia.generazionemangav3.ui.theme.fontFamily

@Composable
fun PaperScreen(viewModel: AppViewModel, navController: NavController) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "near_you") {
        composable("near_you") {
            NearYou(navController)
        }
        composable(Screen.Map.route) {
            MapScreen(viewModel,navController)
        }
    }

/*
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
        brush = Brush.linearGradient(
            colors = listOf(
                Violet40, Violet20
            )
        )
    )) {
        NearYou()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .padding(top = 80.dp)
                .background(color = MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Solo Leveling",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 30.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                style = TextStyle(shadow = Shadow(color = Color.Black, blurRadius = 5F))
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 130.dp)
                .background(color = MaterialTheme.colorScheme.secondary),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(horizontalArrangement = Arrangement.Start) {
                IconButton(
                    onClick = {
                        //navController.navigate(Screen.MangaPage.route)
                    },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Volume 2",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 30.sp,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    style = TextStyle(shadow = Shadow(color = Color.Black, blurRadius = 5F))
                )
            }
        }
    }

 */
}

@Composable
fun NearYou(navController: NavController) {
    Box(modifier = Modifier
        .padding(top = 60.dp, bottom = 30.dp)) {
        Row(horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = {
                    navController.navigateUp()
                },
                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.tertiary)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            androidx.compose.material3.Text(
                text = " Shops Near You",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 24.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal
            )
        }
        LazyColumn (modifier = Modifier
            .padding(top = 40.dp),

            ){
            itemsIndexed(listOf(
                "Star Shop - Via Po, 12",
                "Otherworld Fumetteria - Via S. Quintino, 6/N",
                "Tales of Comics - Via S. Marino, 85",
                "Funside - Via Antonio Bertola, 31f"
            )
            ){
                index, string ->
                    Card ( modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .padding(start = 10.dp, end = 10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        ),
                        onClick ={ navController.navigate(Screen.Map.route)})
                    {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ){
                            androidx.compose.material3.Text(
                                text = string,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 20.sp,
                                fontFamily = fontFamily,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier
                                    .padding(10.dp)
                            )

                        }
                    }

            }

        }
    }
}



