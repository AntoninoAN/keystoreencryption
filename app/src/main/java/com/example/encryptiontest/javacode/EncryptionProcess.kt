package com.example.encryptiontest.javacode

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class EncryptionProcess {
    companion object{
        const val TRANSFORMATION = "AES/GCM/NoPadding"
        const val ANDROID_KEY_STORE = "AndroidKeyStore"
    }
    private lateinit var encryption: ByteArray
    private lateinit var iv: ByteArray

    fun encrypText(alias: String, textToEncrypt: String): ByteArray{
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(alias))

        iv = cipher.iv

        encryption = cipher.doFinal(textToEncrypt.toByteArray())
        return encryption
    }

    fun getSecretKey(alias: String): SecretKey{

        var keyGenerator= KeyGenerator
            .getInstance(KeyProperties.KEY_ALGORITHM_AES)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyGenerator= KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                ANDROID_KEY_STORE)
            keyGenerator.init(
                KeyGenParameterSpec.Builder(alias,
                    KeyProperties.PURPOSE_ENCRYPT.or(KeyProperties.PURPOSE_DECRYPT))
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build())
        }else{
            keyGenerator.init(
                SecureRandom.getInstance(alias, ANDROID_KEY_STORE)
            )
        }

        return keyGenerator.generateKey()
    }

    fun getEncryption(): ByteArray= encryption


    fun getIv(): ByteArray= iv

}