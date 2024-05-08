import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.polito.giulia.generazionemangav3.AppViewModel
import com.polito.giulia.generazionemangav3.screens.Lang
import com.polito.giulia.generazionemangav3.screens.LanguageSelection
import com.polito.giulia.generazionemangav3.ui.theme.Screen
import com.polito.giulia.generazionemangav3.ui.theme.Violet20
import com.polito.giulia.generazionemangav3.ui.theme.Violet40
import com.polito.giulia.generazionemangav3.ui.theme.fontFamily

//import per popup
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties


//funzione per far comparire popup di dialogo
@Composable
fun PopupBox(popupWidth: Float, popupHeight:Float, showPopup: Boolean, onClickOutside: () -> Unit, content: @Composable() () -> Unit) {

    if (showPopup) {
        // full screen background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .zIndex(10F),
            contentAlignment = Alignment.Center
        ) {
            // popup
            Popup(
                alignment = Alignment.Center,
                properties = PopupProperties(
                    excludeFromSystemGesture = true,
                ),
                // to dismiss on click outside
                onDismissRequest = { onClickOutside() }
            ) {
                Box(
                    Modifier
                        .width(350.dp)
                        .height(450.dp)
                        .background(Color.White)
                        .clip(RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    }
}



@Composable
fun SettingsScreen(viewModel: AppViewModel, navController: NavController) {
    val selectedLanguage = remember { mutableStateOf<Lang?>(null)}
    var checked by remember { mutableStateOf(true) }
    //per popup
    var showPopup by rememberSaveable { mutableStateOf(false) }
    var showPopupLogout by rememberSaveable { mutableStateOf(false) }
    //per messaggio
    val ctx = LocalContext.current
    var openAlertDialog by remember { mutableStateOf(false) }

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
                modifier = Modifier.padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "Language App Settings:",
                        textAlign = TextAlign.Left,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                    Row() {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                LanguageSelection(selectedLanguage = selectedLanguage.value) { lang ->
                                    selectedLanguage.value = lang
                                }
                            }
                    }

                }
            }

            //sezione notifiche

            Row(
                modifier = Modifier.padding(20.dp)
            ) {
                    Text(
                        text = "Notifications:",
                        textAlign = TextAlign.Left,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                    Switch(
                        modifier = Modifier.size(50.dp),
                        checked = checked,
                        onCheckedChange = {
                                checked = it
                            },
                        colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.tertiary,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                        uncheckedThumbColor = Color.Gray,
                        uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                            ),

                    )
            }

            //sezione di logout
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 300.dp)
            ) {
                Row(
                ) {
                    androidx.compose.material3.Button(
                        onClick = { showPopupLogout = true },
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Text(
                            "Logout",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    }

                }
                Row() {
                    androidx.compose.material3.Text(
                        text = "Delete your account",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.clickable{
                            showPopup = true
                        }
                    )
                }
            }
        }
    }
    //per popup delete account
    PopupBox(popupWidth = 200F,
        popupHeight = 300F,
        showPopup = showPopup,
        onClickOutside = {showPopup = false},
        content = {
            Box(

            ){
                //icona
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.align(alignment = Alignment.TopEnd)
                ) {
                    IconButton(
                        onClick = { showPopup = false },
                        modifier = Modifier.padding(10.dp,0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(150.dp)
                            )
                    }
                }
                //testi

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = "WARNING: This CANNOT be undone.",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        modifier = Modifier.padding(10.dp, 90.dp),
                        color = Color.Red,
                        textAlign = TextAlign.Center)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = "Are you sure you want to delete your account?",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(10.dp, 180.dp),
                        color = Color.Black,
                        textAlign = TextAlign.Center)
                }

                //bottoni
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(60.dp,10.dp).align(alignment = Alignment.BottomCenter),

                ) {
                    androidx.compose.material3.Button(
                        onClick = {
                            navController.navigate(Screen.Login.route)
                            Toast.makeText(ctx, "Account successfully deleted!", Toast.LENGTH_SHORT).show()
                            openAlertDialog = false
                        }
                    ) {
                        Text("YES",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 20.sp,
                            color = Color.White)
                    }
                    androidx.compose.material3.Button(
                        onClick = {showPopup = false },
                        colors = ButtonDefaults.buttonColors((MaterialTheme.colorScheme.tertiary))
                    ) {
                        Text("NO",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 20.sp,
                            color = Color.White,
                            )
                    }

                }
            }

        }
    )


    //per popup LOGOUT
    PopupBox(popupWidth = 200F,
        popupHeight = 300F,
        showPopup = showPopupLogout,
        onClickOutside = {showPopupLogout = false},
        content = {
            Box(

            ){
                //icona
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.align(alignment = Alignment.TopEnd)
                ) {
                    IconButton(
                        onClick = { showPopupLogout = false },
                        modifier = Modifier.padding(10.dp,0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(150.dp)
                        )
                    }
                }
                //testi
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = "Are you sure you want to logout?",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(10.dp, 180.dp),
                        color = Color.Black,
                        textAlign = TextAlign.Center)
                }

                //bottoni
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(60.dp,10.dp).align(alignment = Alignment.BottomCenter),

                    ) {
                    androidx.compose.material3.Button(
                        onClick = {
                            navController.navigate(Screen.Login.route)
                            Toast.makeText(ctx, "Logout successful!", Toast.LENGTH_SHORT).show()
                            openAlertDialog = false
                        }
                    ) {
                        Text("YES",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 20.sp,
                            color = Color.White)
                    }
                    androidx.compose.material3.Button(
                        onClick = {showPopupLogout = false },
                        colors = ButtonDefaults.buttonColors((MaterialTheme.colorScheme.tertiary))
                    ) {
                        Text("NO",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 20.sp,
                            color = Color.White,
                        )
                    }

                }
            }

        }
    )


}