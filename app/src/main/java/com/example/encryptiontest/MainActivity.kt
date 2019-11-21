package com.example.encryptiontest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import com.example.encryptiontest.javacode.DecryptionProcess
import com.example.encryptiontest.javacode.EncryptionProcess
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var decryptor: DecryptionProcess
    lateinit var encryptor: EncryptionProcess

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        decryptor = DecryptionProcess()
        encryptor = EncryptionProcess()

        btn_process_encrypt.setOnClickListener {
            encrypText(et_encrypt.text.toString())
        }
        btn_process_decrypt.setOnClickListener { decrypText() }
    }

    fun decrypText() {
        tv_result.text = (
            decryptor.decryptData(
                "MISAL",
                encryptor.getEncryption(),
                encryptor.getIv()
            )
        )
    }

    fun encrypText(value: String) {
        var encrypted = encryptor.encrypText("MISAL", value)
        tv_result.text =(Base64.encodeToString(encrypted, Base64.DEFAULT))
    }
}
