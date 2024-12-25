package com.serhiiromanchuk.mastermeme.navigaion

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    data object Home : Screen(ROUTE_HOME)
    data object Editor : Screen(ROUTE_EDITOR) {
        const val MEME_RES_ID = "meme_res_id"
        val routeWithArgs = "$route/{$MEME_RES_ID}"
        val arguments = listOf(
            navArgument(MEME_RES_ID) { type = NavType.IntType }
        )
    }

    companion object {
        private const val ROUTE_HOME = "home_screen"
        private const val ROUTE_EDITOR = "editor_screen"
    }
}