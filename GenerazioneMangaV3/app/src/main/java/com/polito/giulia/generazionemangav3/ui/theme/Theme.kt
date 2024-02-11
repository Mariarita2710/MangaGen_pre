package com.polito.giulia.generazionemangav3.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Violet40, //colore sfondo
    onPrimary = White00,
    primaryContainer = Indigo40, //colore caselle
    secondary = Violet20, //colore elementi in risalto
    onSecondary = White00,
    secondaryContainer = Red20, //colore logout
    tertiary = Coral40, //colore dettagli icone e bottoni
    onTertiary = Violet40,
    background = Indigo90, //sfondo secondario
    onBackground = Black00,
    outline = Black00,
    outlineVariant = Coral40,
    tertiaryContainer = Red40, // heart button
    errorContainer = Red40,
    onErrorContainer = White00,
)

@Composable
fun GenerazioneMangaV3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, //FALSE perchè non vogliamo i colori dinamici
    content: @Composable () -> Unit
) {

    val colorScheme = DarkColorScheme //----> ASSEGNA IL TEMA ALL'APP CHE SARA SEMPRE QUELLO DARK
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Black.toArgb()  //DarkColorScheme.primary.toArgb() //ContextCompat.getColor(view.context, R.color.primary)
            window.navigationBarColor = Color.Black.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}