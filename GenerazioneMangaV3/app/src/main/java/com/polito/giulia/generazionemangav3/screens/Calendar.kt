package com.polito.giulia.generazionemangav3.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polito.giulia.generazionemangav3.AppViewModel
import com.polito.giulia.generazionemangav3.ui.theme.Blue40
import com.polito.giulia.generazionemangav3.ui.theme.Violet20
import com.polito.giulia.generazionemangav3.ui.theme.Violet40
import com.polito.giulia.generazionemangav3.ui.theme.fontFamily
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCalendar(navController: NavController,viewModel: AppViewModel){
// Decoupled snackbar host state from scaffold state for demo purposes.
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackState, Modifier)
    val openDialog = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            // Blocks Sunday and Saturday from being selected.
           /* override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val dayOfWeek = Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneId.of("UTC"))
                        .toLocalDate().dayOfWeek
                    dayOfWeek != DayOfWeek.SUNDAY && dayOfWeek != DayOfWeek.SATURDAY
                } else {
                    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    calendar.timeInMillis = utcTimeMillis
                    calendar[Calendar.DAY_OF_WEEK] != Calendar.SUNDAY &&
                            calendar[Calendar.DAY_OF_WEEK] != Calendar.SATURDAY
                }
            }*/

            // Allow selecting dates from year 2024 forward.
            override fun isSelectableYear(year: Int): Boolean {
                return year > 2023
            }
        }
    )

// TODO demo how to read the selected date from the state.
    if (openDialog.value) {
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }
        DatePickerDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
            },
            tonalElevation = 30.dp,
            colors = DatePickerDefaults.colors(Blue40),
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        snackScope.launch {
                            snackState.showSnackbar(
                                "Selected date timestamp: ${datePickerState.selectedDateMillis}"
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
                    enabled = confirmEnabled.value
                ) {
                    Text("OK",
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                TextButton(
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Cancel",
                        color = Color.White
                        )
                }
            }
        ) {
            DatePicker(state = datePickerState, colors = DatePickerDefaults.colors(MaterialTheme.colorScheme.tertiary))
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Violet40, Violet20
                )
            )
        )) {
        /*
        Row (verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Button(
                modifier = Modifier.padding(top=200.dp),
                onClick = {openDialog.value=true},
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
            ) {
                Text(text = "Pick a date")
            }
        }*/
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 20.dp)
        ) {
            /*Text(
                "Selected date timestamp: ${datePickerState.selectedDateMillis ?: "no selection"}",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )*/
            DatePicker(state = datePickerState, modifier = Modifier.padding(8.dp))
            Divider(
                color = MaterialTheme.colorScheme.primary, thickness = 1.dp,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
            Box(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp, 8.dp, 16.dp, 100.dp),
                contentAlignment = Alignment.TopStart) {
                Column(
                    //horizontalAlignment = Alignment.Start,
                    //verticalArrangement = Arrangement.Top
                ) {
                    val cl = Calendar.getInstance()
                    if (datePickerState.selectedDateMillis != null) {
                        cl.timeInMillis =
                            datePickerState.selectedDateMillis!! //here your time in miliseconds
                    }
                    val month = cl[Calendar.MONTH].toLong() + 1
                    val date =
                        "" + cl[Calendar.DAY_OF_MONTH] + "/" + month.toString() + "/" + cl[Calendar.YEAR]
                   /* Text(
                        text = date,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 24.sp,
                        fontFamily = fontFamily,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal
                    )*/
                    if (date == "12/3/2024") {
                        Text(
                            text = "New Releases: ",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 24.sp,
                            fontFamily = fontFamily,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = "• One Piece Volume 105",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 18.sp,
                            fontFamily = fontFamily,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal
                        )
                    } else if (date == "19/3/2024") {
                        Text(
                            text = "New Releases: ",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 24.sp,
                            fontFamily = fontFamily,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = "• Spy x Family Volume 11",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 18.sp,
                            fontFamily = fontFamily,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal
                        )
                    } else if (date == "2/4/2024") {
                        Text(
                            text = "New Releases: ",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 24.sp,
                            fontFamily = fontFamily,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = "• Boruto: Naruto Next Generations Volume 19" +
                                    "\n• Tokyo Revengers Volume 19-20" +
                                    "\n• That Time I Got Reincarnated as a Slime Omnibus 1 Volume 1-3" +
                                    "\n• Moriarty the Patriot Volume 15",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 18.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    } else if (date == "16/4/2024") {
                        Text(
                            text = "New Releases: ",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 24.sp,
                            fontFamily = fontFamily,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = "• Jujutsu Kaisen Volume 22",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 18.sp,
                            fontFamily = fontFamily,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal
                        )
                    } else {
                        Text(
                            text = "No new releases",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 24.sp,
                            fontFamily = fontFamily,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}
