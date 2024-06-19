package com.polito.giulia.generazionemangav3.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polito.giulia.generazionemangav3.AppViewModel
import com.polito.giulia.generazionemangav3.ui.theme.Coral60
import com.polito.giulia.generazionemangav3.ui.theme.Screen
import com.polito.giulia.generazionemangav3.ui.theme.Violet20
import com.polito.giulia.generazionemangav3.ui.theme.Violet40
import com.polito.giulia.generazionemangav3.ui.theme.fontFamily

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun Login(viewModel: AppViewModel, navController : NavController){

     Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Violet40, Violet20
                    )
                )
            )
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "MANGA GEN",
            modifier = Modifier
                .width(250.dp)
                .height(100.dp)
                .align(Alignment.CenterHorizontally),
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 40.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(36.dp))
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = "Log In to access",
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                color = Color.White
            )
        }
         val emailValue = remember { mutableStateOf(TextFieldValue()) }
         val passwordValue = remember { mutableStateOf(TextFieldValue()) }
         val emailDomain = emailValue.value.text.trim().substringAfterLast('@')
         Spacer(modifier = Modifier.height(36.dp))
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(
                value = emailValue.value,
                shape = MaterialTheme.shapes.large,
                placeholder =
                {
                   Text(
                            text = "Email",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color.White
                    )
                },
                onValueChange = {
                    emailValue.value = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(26.dp))
            OutlinedTextField(
                value = passwordValue.value,
                visualTransformation = PasswordVisualTransformation(),
                shape = MaterialTheme.shapes.large,
                placeholder =
                {
                    Text(
                        text = "Password",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.White
                    )
                },
                onValueChange = {
                    passwordValue.value = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(26.dp))
            Button(
                onClick = {
                    navController.navigate(Screen.Home.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Coral60, shape = MaterialTheme.shapes.large)
            ) {
                Text(
                    text = "Login",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.White
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
            ) {
                Spacer(modifier = Modifier.height(42.dp))
                Text(
                    text = "Don't have an account?",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.width(4.dp)) // Aggiungi spazio tra i due testi
                Text(
                    text = "Sign Up",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.clickable(onClick = {
                    })
                )

            }
        }
    }

}