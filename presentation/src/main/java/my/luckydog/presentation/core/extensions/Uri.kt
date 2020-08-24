package my.luckydog.presentation.core.extensions

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

fun Uri.fileMimeType(context: Context): String? = fileMimeType(context.contentResolver)

fun Uri.fileMimeType(resolver: ContentResolver): String? {
    return if (scheme == ContentResolver.SCHEME_CONTENT) {
        val mime = MimeTypeMap.getSingleton()
        resolver.getType(this)?.let {
            mime.getExtensionFromMimeType(it)
        }
    } else MimeTypeMap.getFileExtensionFromUrl(path)
}

fun Uri.toInputStream(context: Context): InputStream? = toInputStream(context.contentResolver)

fun Uri.toInputStream(resolver: ContentResolver): InputStream? = try {
    resolver.openInputStream(this)
} catch (ex: FileNotFoundException) {
    null
}

fun Uri.copyToFile(context: Context, file: File): Boolean {
    val inputStream = toInputStream(context)
    val outputStream = file.toOutputStream()

    return if (outputStream != null && inputStream != null) {
        try {
            inputStream.use { input -> outputStream.use { output -> input.copyTo(output) } }
        } catch (ex: IOException) {
            return false
        }
        true
    } else false
}