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
import androidx.navigation.NavController
import com.polito.giulia.generazionemangav3.AppViewModel
import com.polito.giulia.generazionemangav3.screens.Lang
import com.polito.giulia.generazionemangav3.screens.LanguageSelection
import com.polito.giulia.generazionemangav3.ui.theme.Screen
import com.polito.giulia.generazionemangav3.ui.theme.Violet20
import com.polito.giulia.generazionemangav3.ui.theme.Violet40
import com.polito.giulia.generazionemangav3.ui.theme.fontFamily

@Composable
fun SettingsScreen(viewModel: AppViewModel, navController: NavController) {
    val selectedLanguage = remember { mutableStateOf<Lang?>(null)}

    var checked by remember { mutableStateOf(true) }


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
                        onClick = { navController.navigate(Screen.Login.route) },
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
                        //TODO che succede quando lo clicco?
                        text = "Delete your account",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate(Screen.Login.route)
                        })
                    )
                }
            }
        }
    }
}