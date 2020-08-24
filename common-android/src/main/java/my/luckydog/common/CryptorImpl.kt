package my.luckydog.common

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.*
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.AeadKeyTemplates
import com.google.crypto.tink.aead.AeadWrapper
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


class CryptorImpl(alias: String): Cryptor {

    private companion object{
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
    }

    private lateinit var iv: ByteArray
    private lateinit var encryption: ByteArray

    private lateinit var secretKey:SecretKey

    private lateinit var aead: Aead
    private lateinit var aad: String

    init {
        AeadConfig.register()
        //secretKey= getSecretKey(alias)
        val keysetHandle = KeysetHandle.generateNew(AeadKeyTemplates.AES128_GCM)
        AeadWrapper.register()
        aead = keysetHandle.getPrimitive(Aead::class.java)
        aad="qwerty123";

    }

    @ExperimentalStdlibApi
    override fun encryptText(textToEncrypt: String): ByteArray {

        /*val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return enCipher.doFinal(textToEncrypt.toByteArray(Charsets.UTF_8))*/

        return aead.encrypt(textToEncrypt.encodeToByteArray(), aad.encodeToByteArray());
    }

    @ExperimentalStdlibApi
    override fun decryptData(encryptedData: ByteArray): String {

        /*val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        return String(deCipher.doFinal(encryptedData), Charsets.UTF_8)*/
        return String(aead.decrypt(encryptedData, aad.encodeToByteArray()))
    }

    private fun getSecretKey(alias: String): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
        keyGenerator.init(
            KeyGenParameterSpec.Builder(alias, PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
                .setBlockModes(BLOCK_MODE_GCM)
                .setEncryptionPaddings(ENCRYPTION_PADDING_NONE)
                .build()
        )
        return keyGenerator.generateKey()
    }
}