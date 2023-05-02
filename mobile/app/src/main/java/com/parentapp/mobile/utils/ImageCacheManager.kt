package com.parentapp.mobile.utils

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ImageCacheManager @Inject constructor(@ApplicationContext context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("image_cache_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val IMAGE_CACHE_KEY_PREFIX = "image_cache_key_"
        private const val DEFAULT_COMPRESS_QUALITY = 100
    }

    fun cacheImage(url: String, bitmap: Bitmap) {
        val editor = prefs.edit()
        val key = getImageCacheKey(url)
        val encodedBitmap = encodeBitmap(bitmap)
        editor.putString(key, encodedBitmap)
        editor.apply()
    }

    fun getCachedImage(url: String): Bitmap? {
        val key = getImageCacheKey(url)
        val encodedBitmap = prefs.getString(key, null)
        return if (encodedBitmap != null) decodeBitmap(encodedBitmap) else null
    }

    fun clearCache() {
        prefs.edit().clear().apply()
    }
    private fun getImageCacheKey(url: String): String {
        return IMAGE_CACHE_KEY_PREFIX + url.hashCode()
    }
    private fun encodeBitmap(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, DEFAULT_COMPRESS_QUALITY, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun decodeBitmap(encodedBitmap: String): Bitmap {
        val byteArray = Base64.decode(encodedBitmap, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}