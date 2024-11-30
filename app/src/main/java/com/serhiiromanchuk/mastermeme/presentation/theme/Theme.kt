package com.serhiiromanchuk.mastermeme.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current as ComponentActivity

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            context.enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.dark(MasterMemeLightBlack.toArgb()),
                navigationBarStyle = SystemBarStyle.dark(MasterMemeBlack.toArgb())
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }

//            val windowsInsetsController = WindowCompat.getInsetsController(window, view)
//            windowsInsetsController.isAppearanceLightStatusBars = true
//            windowsInsetsController.isAppearanceLightNavigationBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
