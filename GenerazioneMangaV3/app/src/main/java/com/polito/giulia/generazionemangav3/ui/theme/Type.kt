package com.polito.giulia.generazionemangav3.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.polito.giulia.generazionemangav3.R


val fontFamily = FontFamily(
Font(R.font.poppins_bold, FontWeight.Bold),
Font(R.font.poppins_bold_italic, FontWeight.Bold, FontStyle.Italic),
Font(R.font.poppins_italic, FontWeight.Normal, FontStyle.Italic),
Font(R.font.poppins_light, FontWeight.Light),
Font(R.font.poppins_regular, FontWeight.Normal),
Font(R.font.poppins_light_italic, FontWeight.Light, FontStyle.Italic)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)