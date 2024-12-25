package com.serhiiromanchuk.mastermeme.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.serhiiromanchuk.mastermeme.navigaion.RootAppNavigation
import com.serhiiromanchuk.mastermeme.navigaion.rememberNavigationState
import com.serhiiromanchuk.mastermeme.presentation.theme.MasterMemeBlack
import com.serhiiromanchuk.mastermeme.presentation.theme.MasterMemeLightBlack
import com.serhiiromanchuk.mastermeme.presentation.theme.MasterMemeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(MasterMemeLightBlack.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(MasterMemeBlack.toArgb())
        )

        setContent {
            MasterMemeTheme {
                val navigationState = rememberNavigationState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    RootAppNavigation(
                        modifier = Modifier.padding(16.dp),
                        navigationState = navigationState
                    )
                }
            }
        }
    }
}