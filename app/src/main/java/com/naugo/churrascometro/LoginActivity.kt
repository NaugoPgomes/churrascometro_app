package com.naugo.churrascometro

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.button

class LoginActivity : AppCompatActivity() {

    private lateinit var user: FirebaseAuth

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        user = FirebaseAuth.getInstance()

        verificarSeoUsuarioEstaLogado()


        button.setOnClickListener{
            Logar()
        }

        cadastro_tela_login.setOnClickListener{
            startActivity(Intent(this, CadastroActivity::class.java))
        }

    }

    private fun verificarSeoUsuarioEstaLogado()
    {
        if(user.currentUser !==null)
        {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }


    private fun Logar()
    {
        val email = email.text.toString()
        val senha = senha.text.toString()

        if(email.isNotEmpty() && senha.isNotEmpty())
        {
            user.signInWithEmailAndPassword(email,senha)
                .addOnCompleteListener {mTask ->

                    if (mTask.isSuccessful)
                    {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this, mTask.exception!!.message, Toast.LENGTH_SHORT).show()
                    }

                }
        }
        else
        {
            Toast.makeText(this, "Email e password Não podem estar vazios", Toast.LENGTH_SHORT).show()
        }
    }
}