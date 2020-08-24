package my.luckydog.presentation.core.extensions

import java.io.*

fun File.toOutputStream(): OutputStream? = try {
    FileOutputStream(this)
} catch (ex: FileNotFoundException) {
    null
}

fun File.copyInputStreamToFile(inputStream: InputStream) {
    this.outputStream().use { fileOut -> inputStream.copyTo(fileOut) }
}

fun File.createFile(name: String): File? {
    val file = File(this, name)

    return if (file.exists()) file else try {
        if (file.createNewFile()) file else null
    } catch (ex: IOException) {
        null
    }
}

fun File.createDir(name: String): File? {
    val dir = File(this, name)
    return if (dir.exists()) dir else try {
        if (dir.mkdirs()) dir else null
    } catch (ex: IOException) {
        null
    }
}

fun File.createFile(dirName: String, fileName: String): File? {
    return createDir(dirName)?.createFile(fileName)
}

/*Single.create<List<File>> { emitter ->

            val f = File(requireActivity().getCacheDir().path + "/aaa.jpg");
            f.copyInputStreamToFile(requireActivity().getAssets().open("aaa.jpg"))

            val f2 = File(requireActivity().getCacheDir().path + "/abc.jpeg");
            f2.copyInputStreamToFile(requireActivity().getAssets().open("a1.jpg"))

            val f3 = File(requireActivity().getCacheDir().path + "/girl.jpg");
            f3.copyInputStreamToFile(requireActivity().getAssets().open("abc.jpeg"))


            emitter.onSuccess(listOf(f, f3, f2))
        }*/