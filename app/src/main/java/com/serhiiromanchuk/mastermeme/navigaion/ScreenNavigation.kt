package com.serhiiromanchuk.mastermeme.navigaion

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.EditorScreenRoot
import com.serhiiromanchuk.mastermeme.presentation.screens.home.HomeScreenRoot

fun NavGraphBuilder.homeRoute(
    modifier: Modifier = Modifier,
    navigationState: NavigationState
) {
    composable(route = Screen.Home.route) {
        HomeScreenRoot(
            modifier = modifier,
            navigationState = navigationState
        )
    }
}

fun NavGraphBuilder.editorRoute(
    modifier: Modifier,
    navigationState: NavigationState
) {
    composable(
        route = Screen.Editor.routeWithArgs,
        arguments = Screen.Editor.arguments
    ) { navBackStackEntry ->
        val memeRes = navBackStackEntry.arguments?.getInt(Screen.Editor.MEME_RES_ID) ?: -1
        EditorScreenRoot(
            modifier = modifier,
            navigationState = navigationState,
            memeResId = memeRes
        )
    }
}
