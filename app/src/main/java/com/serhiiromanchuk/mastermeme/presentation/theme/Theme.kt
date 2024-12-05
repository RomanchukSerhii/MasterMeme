package com.serhiiromanchuk.mastermeme.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView

private val DarkColorScheme = darkColorScheme(
    primary = MasterMemeLightPurple,
    background = MasterMemeBlack,
    surface = MasterMemeLightBlack,
    secondary = MasterMemeGray,
    outline = MasterMemeDarkGray,
    onPrimary = MasterMemeDarkPurple,
    onSurface = MasterMemeLightGray,
)

@Composable
fun MasterMemeTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
