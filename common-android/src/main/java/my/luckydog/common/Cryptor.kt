package my.luckydog.common

interface Cryptor {

    fun encryptText(textToEncrypt: String): ByteArray

    fun decryptData(encryptedData: ByteArray): String
}