package com.serhiiromanchuk.mastermeme.presentation.core.utils

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
class StoragePermissionHandler {
    fun handleWriteStoragePermissions(
        writeStorageAccessState: MultiplePermissionsState,
        coroutineScope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
        onPermissionGranted: () -> Unit
    ) {
        when {
            writeStorageAccessState.allPermissionsGranted -> {
                onPermissionGranted()
            }

            writeStorageAccessState.shouldShowRationale -> {
                coroutineScope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "The storage permission is needed to save the image",
                        actionLabel = "Grant Access"
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        writeStorageAccessState.launchMultiplePermissionRequest()
                    }
                }
            }

            else -> {
                writeStorageAccessState.launchMultiplePermissionRequest()
            }
        }
    }
}