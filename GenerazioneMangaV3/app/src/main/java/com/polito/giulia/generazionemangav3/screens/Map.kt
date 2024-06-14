package com.polito.giulia.generazionemangav3.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polito.giulia.generazionemangav3.AppViewModel
import com.polito.giulia.generazionemangav3.R
import com.polito.giulia.generazionemangav3.ui.theme.Violet20
import com.polito.giulia.generazionemangav3.ui.theme.Violet40
import com.polito.giulia.generazionemangav3.ui.theme.fontFamily

@Composable
fun MapScreen(viewModel: AppViewModel, navController: NavController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Violet40, Violet20
                )
            )
        ),
        //.padding(top=100.dp, bottom=100.dp, start = 20.dp, end = 20.dp )
        contentAlignment = Alignment.Center
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.verticalScroll(rememberScrollState()).padding(bottom = 90.dp)) {
            Text(
                text = "Map",
                modifier = Modifier.padding(top = 40.dp) ,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 40.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                style = TextStyle(shadow = Shadow(color = Color.Black, blurRadius = 5F))
            )
            Card(elevation = CardDefaults.elevatedCardElevation(20.dp)){
                Image(
                    painter = painterResource(id = R.drawable.mappa_manga),
                    contentDescription = "mappa",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(400.dp)
                )
            }
        }
    }
}