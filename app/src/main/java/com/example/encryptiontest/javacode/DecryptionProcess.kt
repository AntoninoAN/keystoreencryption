package com.example.encryptiontest.javacode

import java.nio.charset.Charset
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class DecryptionProcess {

    companion object{
        const val ANDROID_KEY_STORE = "AndroidKeyStore"
        const val TRANSFORMATION = "AES/GCM/NoPadding"
    }
    private lateinit var keyStore: KeyStore

    init{
        keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
        keyStore.load(null)
    }

    fun decryptData(alias: String, encryptedData: ByteArray, encryptionIv: ByteArray): String{

        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(128, encryptionIv)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(alias), spec)

        return String(cipher.doFinal(encryptedData), Charset.defaultCharset())
    }

    private fun getSecretKey(alias: String): SecretKey {
        return ( keyStore.getEntry(alias, null) as KeyStore.SecretKeyEntry).secretKey
    }
}