package com.foke.together.util

import android.content.Context
import android.content.Intent
import android.content.Intent.createChooser
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toFile
import androidx.print.PrintHelper
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.coroutines.resume

object ImageFileUtil {

    suspend fun cacheBitmap(
        context: Context,
        bitmap: Bitmap,
        fileName: String
    ): Uri {
        val uri = bitmap.cache(context, fileName)
        return uri
    }

    suspend fun saveBitmapToStorage(
        context: Context,
        bitmap: Bitmap,
        fileName: String
    ): Uri {
        val uri = bitmap.saveToStorage(context, fileName)
        return uri
    }

    private suspend fun Bitmap.cache(context: Context, fileName: String): Uri {
        val file = File(
            context.cacheDir,
            fileName + ".jpg"
        )
        file.writeBitmap(this, Bitmap.CompressFormat.JPEG, 100)

        return Uri.fromFile(file) ?: Uri.EMPTY
    }

    private suspend fun Bitmap.saveToStorage(context: Context, fileName: String): Uri {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            fileName + ".jpg"
        )

        file.writeBitmap(this, Bitmap.CompressFormat.JPEG, 100)

        return scanFilePath(context, file.absolutePath) ?: throw Exception("File could not be saved")
    }

    private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }

    /**
     * We call [MediaScannerConnection] to index the newly created image inside MediaStore to be visible
     * for other apps, as well as returning its [MediaStore] Uri
     */
    private suspend fun scanFilePath(context: Context, filePath: String): Uri? {
        return suspendCancellableCoroutine { continuation ->
            MediaScannerConnection.scanFile(
                context,
                arrayOf(filePath),
                arrayOf("image/jpg")
            ) { _, scannedUri ->
                if (scannedUri == null) {
                    continuation.cancel(Exception("File $filePath could not be scanned"))
                } else {
                    continuation.resume(scannedUri)
                }
            }
        }
    }
    
    // TODO: 파일 분리하기
    fun shareUri(context: Context, uri: Uri) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/jpg"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(context, createChooser(intent, "Share your image"), null)
    }

    fun getBitmapFromUri(context: Context, uri: Uri): Bitmap {
        val file = uri.toFile()
        return BitmapFactory.decodeFile(file.absolutePath)
    }

    fun printFromUri(context: Context, uri: Uri){
        // can use getActivity()
        val file = uri.toFile()
        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
        val photoPrinter = PrintHelper(context)
        photoPrinter.printBitmap("Print", bitmap)
    }
}