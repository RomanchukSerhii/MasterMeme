package com.serhiiromanchuk.mastermeme.presentation.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.coroutines.resume

class BitmapProcessor(private val context: Context) {
    /**
     * Creates a Bitmap based on a Picture.
     */
    fun createBitmapFromPicture(picture: Picture): Bitmap {
        val bitmap = Bitmap.createBitmap(
            picture.width,
            picture.height,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        canvas.drawPicture(picture)

        return bitmap
    }

    /**
     * Saves the Bitmap to disk and returns the URI of the saved file.
     */
    suspend fun saveBitmapToDisk(bitmap: Bitmap): Uri {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "new_meme-${System.currentTimeMillis()}.jpg"
        )

        file.writeBitmap(bitmap, Bitmap.CompressFormat.JPEG, 90)

        return scanFilePath(context, file.path) ?: throw Exception("File could not be saved")
    }

    /**
     * Helper function for writing Bitmap to file.
     */
    private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }

    /**
     * Scans the file so that it is visible in the gallery and other media applications.
     */
    private suspend fun scanFilePath(context: Context, filePath: String): Uri? {
        return suspendCancellableCoroutine { continuation ->
            MediaScannerConnection.scanFile(
                context,
                arrayOf(filePath),
                arrayOf("image/png")
            ) { _, scannedUri ->
                if (scannedUri == null) {
                    continuation.cancel(Exception("File $filePath could not be scanned"))
                } else {
                    continuation.resume(scannedUri)
                }
            }
        }
    }
}