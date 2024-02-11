package com.polito.giulia.generazionemangav3.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polito.giulia.generazionemangav3.AppViewModel
import com.polito.giulia.generazionemangav3.ui.theme.Green
import com.polito.giulia.generazionemangav3.ui.theme.Violet20
import com.polito.giulia.generazionemangav3.ui.theme.Violet40
import com.polito.giulia.generazionemangav3.ui.theme.fontFamily

@Composable
fun DigitalScreen(viewModel: AppViewModel, navController: NavController) {
    Chapters(navController)
    /*
    Box(modifier = Modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Violet40, Violet20
                )
            )
            )) {
        Chapters()

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
fun Chapters(navController: NavController) {
    Box(
        modifier = Modifier
            .padding(top = 60.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                text = "Chapters",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 24.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal
            )
        }
        LazyColumn(
            modifier = Modifier
                .padding(top = 40.dp),

            ) {
            items(17) {
                if (it > 0) {
                    Card(
                        modifier = Modifier
                            .width(200.dp)
                            .height(70.dp)
                            .padding(vertical = 10.dp)
                            .padding(start = 10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            androidx.compose.material3.Text(
                                text = "Chapter $it",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 20.sp,
                                fontFamily = fontFamily,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier
                                    .padding(10.dp)
                            )
                            var isChecked = remember { mutableStateOf(false) }
                            IconButton(
                                onClick = {
                                    isChecked.value = !isChecked.value
                                    /*TODO*/
                                },
                                colors = if (isChecked.value) {
                                    IconButtonDefaults.iconButtonColors(contentColor = Green)
                                } else {
                                    IconButtonDefaults.iconButtonColors(contentColor = Color.White)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.CheckCircle,
                                    contentDescription = "checked",
                                    modifier = Modifier.size(50.dp)
                                )
                            }

                        }
                    }
                }

            }
        }
    }
}

