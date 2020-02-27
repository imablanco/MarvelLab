package com.ablanco.marvellab.core.ui.extensions

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.FloatRange
import androidx.annotation.WorkerThread
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-29.
 * MarvelLab.
 */

fun Bitmap.saveToFile(context: Context): File? {
    return runCatching {
        File.createTempFile(System.currentTimeMillis().toString(), ".jpeg").apply {
            FileOutputStream(absolutePath).use {
                compress(Bitmap.CompressFormat.JPEG, 100, it)
            }
        }
    }.getOrNull()
}

fun Bitmap.scale(maxSize: Int): Bitmap {
    val newWidth: Int
    val newHeight: Int
    val bitmapRatio: Float = width / height.toFloat()
    if (bitmapRatio > 1) {
        newWidth = maxSize
        newHeight = ((newWidth / bitmapRatio).toInt())
    } else {
        newHeight = maxSize
        newWidth = ((newHeight * bitmapRatio).toInt())
    }
    return Bitmap.createScaledBitmap(this, newWidth, newHeight, true)
}

@WorkerThread
fun Bitmap.bytes(@FloatRange(from = 0.0, to = 1.0) compression: Float = 1f): ByteArray =
    ByteArrayOutputStream().apply {
        val quality = (compression * 100).toInt()
        compress(Bitmap.CompressFormat.JPEG, quality, this)
    }.toByteArray()