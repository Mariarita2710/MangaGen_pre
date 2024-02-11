package com.polito.giulia.generazionemangav3.screens

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.polito.giulia.generazionemangav3.AppViewModel
import com.polito.giulia.generazionemangav3.R
import com.polito.giulia.generazionemangav3.database
import com.polito.giulia.generazionemangav3.ui.theme.Screen
import com.polito.giulia.generazionemangav3.ui.theme.Violet20
import com.polito.giulia.generazionemangav3.ui.theme.Violet40
import com.polito.giulia.generazionemangav3.ui.theme.fontFamily
import org.intellij.lang.annotations.Language

@Composable
fun VolumePageScreen(viewModel: AppViewModel, number: Int){
    var man = viewModel.selectedManga

    val sl= viewModel.mangas.observeAsState(emptyList()).value
        .filter { it.child("title").value.toString() == man }

    var numVol =0L

    sl.forEach { s ->
        numVol = s.child("numVol").value as Long
    }
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "volume_detail") {
        composable("volume_detail") {
            VolumeDetail(viewModel,navController,number)
        }
        composable(Screen.Digital.route) {
            DigitalScreen(viewModel,navController)
        }
        composable(Screen.Paper.route) {
            PaperScreen(viewModel,navController)
        }
        composable("volume_screen") {
            VolumesPage(navController = navController, numVol)
        }
    }
    //VolumeDetail(viewModel, navController)
}

@Composable
fun VolumeDetail(viewModel: AppViewModel, navController: NavController,number: Int) {
    var man = viewModel.selectedManga
    var numero = number

    val sl= viewModel.mangas.observeAsState(emptyList()).value
        .filter { it.child("title").value.toString() == man }

    Log.d("Manga: ",sl.toString())

    var info=""
    var release=""


    sl.forEach { s ->
        info = s.child("volumes").child(numero.toString()).child("description").value.toString()
        release = s.child("volumes").child(numero.toString()).child("date").value.toString()
       // numbers = s.child("volumes").child("number").value as List<Int>
    }
    Log.d("Uscita numero: ",release)
    MainVolumePage(viewModel, navController, info, release,numero)

    /*
        Box() {
            MainVolumePage(viewModel, navController, info, release)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(top = 80.dp)
                    .background(color = androidx.compose.material3.MaterialTheme.colorScheme.primary),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = man,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
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
                    .background(color = androidx.compose.material3.MaterialTheme.colorScheme.secondary),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(horizontalArrangement = Arrangement.Start) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = androidx.compose.material3.MaterialTheme.colorScheme.tertiary)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = "Volume 1",
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
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
fun MainVolumePage(viewModel: AppViewModel, navController: NavController, info: String, release: String, number: Int) {

    var selectedFormat = remember { mutableStateOf<Format?>(null) }
    var selectedLanguage = remember { mutableStateOf<Lang?>(null) }

    Box(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Violet40, Violet20
                    )
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 40.dp)
        ) {

            var showMore by remember { mutableStateOf(false) }
            Row(horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                              navController.navigate("volume_screen")
                        //navController.navigateUp()
                    },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = androidx.compose.material3.MaterialTheme.colorScheme.tertiary)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Text(
                    text = "Volume " + number ,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
                    fontSize = 26.sp,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    style = TextStyle(shadow = Shadow(color = Color.Black, blurRadius = 5F))
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Release Date", tint = Color.White)
                androidx.compose.material3.Text(
                    text = release,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
                    fontSize = 15.sp,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 3.dp, top = 10.dp, bottom = 8.dp)
                )
            }

            androidx.compose.material3.Text(
                text = "Info",
                color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 15.dp, bottom = 8.dp)
            )
            Card(colors = CardDefaults.cardColors(androidx.compose.material3.MaterialTheme.colorScheme.secondary),) {
                // if showMore is true, the Text will expand
                // Else Text will be restricted to 5 Lines of display
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .animateContentSize(animationSpec = tween(100))
                    .clickable(
                        onClickLabel = "Read more",
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { showMore = !showMore }) {
                    if (showMore) {
                        androidx.compose.material3.Text(text = info)
                    } else {
                        androidx.compose.material3.Text(
                            text = info,
                            maxLines = 5, overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }


            Column {
                androidx.compose.material3.Text(
                    text = "Choose the language*",
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(top = 20.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    /* flags.forEach{f ->
                            IconButton(onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(50.dp)) {
                            FlagCard(painter = f, contentDescription = "flag card",
                                modifier = Modifier
                                    .fillMaxWidth(0.3f)
                                    .padding(5.dp))
                        }
                        */

                    LanguageSelection(selectedLanguage = selectedLanguage.value) { lang ->

                            selectedLanguage.value = lang

                    }
                }
            }

            Column {
                androidx.compose.material3.Text(
                    text = "Format*",
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(top = 18.dp)
                )
                FormatSelection(selectedFormat = selectedFormat.value) { format ->
                    selectedFormat.value = format
                }
            }

            val context= LocalContext.current
            Row(horizontalArrangement = Arrangement.End) {
                Button(
                    onClick = {

                                if (selectedFormat.value == Format.DIGITAL) {
                                    navController.navigate(Screen.Digital.route)
                                } else if (selectedFormat.value == Format.PAPER) {
                                    navController.navigate(Screen.Paper.route)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please, choose a format",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                      },
                    modifier = Modifier.padding(top = 10.dp, bottom = 100.dp, start = 250.dp),
                    colors = ButtonDefaults.buttonColors((androidx.compose.material3.MaterialTheme.colorScheme.tertiary))
                ) {
                    androidx.compose.material3.Text(
                        text = "Search",
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
                        fontSize = 18.sp,
                        fontFamily = fontFamily,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

        }
    }
}

@Composable
fun FlagCard(
    painter: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    lingua: Lang,
    onSelected: () -> Unit
){

    /* IconToggleButton(
        onCheckedChange = {onSelected},
        checked = isSelected,
        modifier = Modifier
            .width(85.dp)
            .height(55.dp),
        colors = IconButtonDefaults.iconToggleButtonColors()
        ){

     */
        /*if(isSelected) {
            Card(
                modifier = Modifier
                    .width(80.dp)
                    .height(50.dp)
                    .padding(5.dp),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.elevatedCardElevation(10.dp),
                border = BorderStroke(2.dp, color = Color.Black)) {
                Image(
                    painter = painter,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop
                )
            }
        } else {
         */
    var selected by remember { mutableStateOf(false) }
            Card(
                onClick = {selected=!selected
                          isSelected},
                modifier =
                if(selected==true){
                    Modifier
                        .alpha(1F)
                        .width(80.dp)
                        .height(50.dp)
                        .padding(5.dp)
                }else{
                    Modifier
                        .alpha(0.7F)
                        .width(80.dp)
                        .height(50.dp)
                        .padding(5.dp)
                },
                shape = RoundedCornerShape(15.dp),
                elevation = if(selected==true){
                CardDefaults.elevatedCardElevation(10.dp)
                } else {
                    CardDefaults.elevatedCardElevation(5.dp)},
                border = if(selected==true){
                    BorderStroke(2.dp, color = Color.Black)
                } else {
                    BorderStroke(0.dp, color = Color.Black)}

                // border = BorderStroke(2.dp, color = Color.Black)
            ) {
                Image(
                    painter = painter,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop
                )
                if(selected){
                    //isLanguageSelected = true
                    Log.d("lingua selezionata: ", lingua.toString())
                }
            }
        }
   // }
//}
enum class Lang{
    ITA, JAP, KOR, ENG
}
@Composable
fun LanguageSelection(
    selectedLanguage : Lang?,
    onLanguageSelected: (Lang?) ->Unit) {

    // var selected = false
    Log.d("lingua selezionata? ", selectedLanguage.toString())
    Log.d("lingua? ", onLanguageSelected.toString())
    val ita = painterResource(id = R.drawable.flag_it)
    val jp = painterResource(id = R.drawable.flag_jp)
    val kr = painterResource(id = R.drawable.flag_kr)
    val uk = painterResource(id = R.drawable.flag_uk)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FlagCard(
            painter = ita,
            contentDescription = "Italian",
            isSelected = selectedLanguage == Lang.ITA,
            lingua = Lang.ITA
        ) {
            onLanguageSelected(Lang.ITA)
        }
        FlagCard(
            painter = jp,
            contentDescription = "Japanese",
            isSelected = selectedLanguage == Lang.JAP,
            lingua = Lang.JAP
        ) {
            onLanguageSelected(Lang.JAP)
        }
        FlagCard(
            painter = kr,
            contentDescription = "Korean",
            isSelected = selectedLanguage == Lang.KOR,
            lingua = Lang.KOR
        ) {
            onLanguageSelected(Lang.KOR)
        }
        FlagCard(
            painter = uk,
            contentDescription = "English",
            isSelected = selectedLanguage == Lang.ENG,
            lingua = Lang.ENG
        ) {
            onLanguageSelected(Lang.ENG)
        }

    }


}

@Composable
fun FormatRadioButton(
    text: String,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            colors = RadioButtonDefaults.colors(
                selectedColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
            ),
            selected = isSelected,
            onClick = onSelected
        )
        androidx.compose.material3.Text(
            text = text,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSecondary,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 18.sp
        )
    }
}

enum class Format{
    PAPER, DIGITAL
}

@Composable
fun FormatSelection(
    selectedFormat : Format?,
    onFormatSelected: (Format?) ->Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        FormatRadioButton(
            text = "Paper",
            isSelected = selectedFormat == Format.PAPER
        ) {
            onFormatSelected(Format.PAPER)
        }
        FormatRadioButton(
            text = "Digital",
            isSelected = selectedFormat == Format.DIGITAL
        ) {
            onFormatSelected(Format.DIGITAL)
        }
    }
}
