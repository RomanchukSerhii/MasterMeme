package com.serhiiromanchuk.mastermeme.navigaion

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost

@Composable
fun RootAppNavigation(
    modifier: Modifier = Modifier,
    navigationState: NavigationState
) {
    NavHost(
        navController = navigationState.navHostController,
        startDestination = Screen.Home.route
    ) {
        homeRoute(
            modifier = modifier,
            navigationState = navigationState
        )
        editorRoute(
            modifier = modifier,
            navigationState = navigationState
        )
    }
}