package com.polito.giulia.generazionemangav3.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.polito.giulia.generazionemangav3.AppViewModel
import com.polito.giulia.generazionemangav3.FindUrl
import com.polito.giulia.generazionemangav3.ui.theme.Violet20
import com.polito.giulia.generazionemangav3.ui.theme.Violet40
import com.polito.giulia.generazionemangav3.ui.theme.fontFamily

@Composable
fun NotificationsScreen(viewModel: AppViewModel, navController: NavController) {
    var url =""
    url = FindUrl(fileName = "Jujutsu Kaisen Cover.jpg")

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 70.dp)
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Violet40, Violet20
                )
            )
        )
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier
                    .padding(20.dp)
                    .background(MaterialTheme.colorScheme.onBackground, RoundedCornerShape(15.dp))
                    .clip(RoundedCornerShape(15.dp))
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(15.dp)
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ){
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .height(70.dp)
                        .width(70.dp)
                        .padding(8.dp)
                ) {
                    AsyncImage(
                        model = url,
                        contentDescription = "icona",
                        contentScale = ContentScale.Crop)
                }


                Text(
                    text = "The second volume of Jujutsu Kaisen is coming tomorrow!",
                    textAlign = TextAlign.Left,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
        
    }
}
