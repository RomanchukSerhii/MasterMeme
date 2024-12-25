package com.serhiiromanchuk.mastermeme.navigaion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(
    val navHostController: NavHostController
) {
    fun navigateTo(route: String) {
        navHostController.navigate(route)
    }

    fun popBackStack() {
        navHostController.popBackStack()
    }

    fun navigateToEditor(memeRes: Int) {
        navHostController.navigate("${Screen.Editor.route}/$memeRes")
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}