package com.serhiiromanchuk.mastermeme.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File

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
    fun saveBitmapToDisk(bitmap: Bitmap): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10+ (API 29+)
            saveImageToMediaStore(bitmap)
        } else {
            // Android 9 and below (API 28 and below)
            saveImageToExternalStorage(bitmap)
        }
    }

    // For Android 10+ (API 29+)
    private fun saveImageToMediaStore(bitmap: Bitmap): Uri {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "new_meme-${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, Constants.MIME_TYPE)
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            ?: throw IllegalStateException("Failed to create MediaStore entry")

        resolver.openOutputStream(uri)?.use { outputStream ->
            if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)) {
                throw IllegalStateException("Failed to save image to MediaStore")
            }
        } ?: throw IllegalStateException("Failed to open output stream for URI")

        return uri
    }

    // For Android 9 and below (API 28 and below)
    private fun saveImageToExternalStorage(bitmap: Bitmap): Uri {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "new_meme-${System.currentTimeMillis()}.jpg"
        )

        file.outputStream().use { out ->
            if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                throw IllegalStateException("Failed to save image to external storage")
            }
        }

        // Оновити медіа-бібліотеку
        MediaScannerConnection.scanFile(
            context,
            arrayOf(file.absolutePath),
            arrayOf(Constants.MIME_TYPE),
            null
        )

        return Uri.fromFile(file)
    }
}