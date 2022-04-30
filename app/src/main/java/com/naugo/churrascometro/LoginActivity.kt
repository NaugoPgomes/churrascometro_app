package com.naugo.churrascometro

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.naugo.churrascometro.verifica.VerificaLeitorDeDigital
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.button
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {


    private lateinit var user: FirebaseAuth

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        user = FirebaseAuth.getInstance()

        verificarSeoUsuarioEstaLogado()

        button.setOnClickListener {
            Logar()
        }

        cadastro_tela_login.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }

        VerificaLeitorDeDigital.PodeLogarComDigital(this)

    }

    private fun mostrarAutenticacao() {
        val executor: Executor = ContextCompat.getMainExecutor(this)

        val biometricPrompt = BiometricPrompt(
            this@LoginActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback()
            {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                }
            }
        )

        val info: BiometricPrompt.PromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Churrascometro")
            .setSubtitle("Desbloqueie seu celular")
            .setNegativeButtonText("Cancelar")
            .build()

        biometricPrompt.authenticate(info)
    }

    private fun verificarSeoUsuarioEstaLogado() {
        if (user.currentUser !== null) {
           mostrarAutenticacao()
        }
    }


    private fun Logar() {
        val email = email.text.toString()
        val senha = senha.text.toString()

        val progressDialog = ProgressDialog(this@LoginActivity)
        progressDialog.setMessage("Carregando...")
        progressDialog.show()

        if (email.isNotEmpty() && senha.isNotEmpty()) {
            user.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener { mTask ->

                    progressDialog.dismiss()

                    if (mTask.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, mTask.exception!!.message, Toast.LENGTH_SHORT).show()
                    }

                }
        } else {
            Toast.makeText(this, "Email e password Não podem estar vazios", Toast.LENGTH_SHORT)
                .show()
        }
    }
}