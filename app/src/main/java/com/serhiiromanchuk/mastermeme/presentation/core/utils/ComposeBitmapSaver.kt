package com.serhiiromanchuk.mastermeme.presentation.core.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import java.io.IOException

class ComposeBitmapSaver(private val context: Context) {

    /**
     * Captures a Composable as a Bitmap.
     * @param content The component to display in the Bitmap.
     * @param width The width of the Bitmap in pixels.
     * @param height The height of the Bitmap in pixels.
     */
    fun captureComposableAsBitmap(content: @Composable () -> Unit, width: Float, height: Float): Bitmap {
        val density = context.resources.displayMetrics.density
        val scaleWidth = (width * density).toInt()
        val scaleHeight = (height * density).toInt()

        val bitmap = Bitmap.createBitmap(scaleWidth, scaleHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val composeView = ComposeView(context).apply {
            setContent { content() }
            measure(
                View.MeasureSpec.makeMeasureSpec(scaleWidth, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(scaleHeight, View.MeasureSpec.EXACTLY)
            )
            layout(0, 0, scaleWidth, scaleHeight)
        }
        composeView.draw(canvas)
        return bitmap
    }

    /**
     * Saves the Bitmap to the device gallery.
     * @param bitmap The Bitmap to save.
     * @param fileName The filename.
     * @return The URI of the saved file, or null if the save failed.
     */
    fun saveBitmapToGallery(bitmap: Bitmap, fileName: String = "meme_with_text.jpg"): Uri? {
        val resolver = context.contentResolver
        val (baseName, extension) = splitFilename(fileName)
        val uniqueFileName = generateUniqueFilename(resolver, baseName, extension)
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, uniqueFileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        imageUri?.let { uri ->
            resolver.openOutputStream(uri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            } ?: throw IOException("Failed to open output stream for URI: $uri")
        }
        return imageUri
    }

    private fun generateUniqueFilename(resolver: ContentResolver, baseName: String, extension: String): String {
        var filename = "$baseName.$extension"
        var count = 1

        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)

        var query = resolver.query(uri, projection, "${MediaStore.MediaColumns.DISPLAY_NAME} = ?", arrayOf(filename), null)
        while (query?.moveToFirst() == true) {
            filename = "$baseName($count).$extension"
            count++
            query.close()
            query = resolver.query(uri, projection, "${MediaStore.MediaColumns.DISPLAY_NAME} = ?", arrayOf(filename), null)
        }
        query?.close()
        return filename
    }

    private fun splitFilename(filename: String): Pair<String, String> {
        val baseName = if (filename.contains(".")) filename.substringBeforeLast(".") else filename
        val extension = if (filename.contains(".")) filename.substringAfterLast(".") else "jpg"
        return baseName to extension
    }
}