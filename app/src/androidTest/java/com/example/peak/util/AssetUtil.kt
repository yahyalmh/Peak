package com.example.peak.util

/**
 * @author yaya (@yahyalmh)
 * @since 23th September 2022
 */

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


object AssetUtil {

    fun readAssetFile(filePath: String): String {
        return try {
            val inputStream: InputStream = getInstrumentation().context.assets.open(filePath)
            readStreamToString(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }

    private fun readStreamToString(
        inputStream: InputStream?,
        charsetName: String? = "UTF-8"
    ): String {
        val bufferSize = 4 * 1024
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, charsetName)
        val buffer = CharArray(bufferSize)
        var length: Int
        while (reader.read(buffer).also { length = it } != -1) {
            builder.append(buffer, 0, length)
        }
        return builder.toString()
    }
}