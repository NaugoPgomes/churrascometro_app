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

class CadastroActivity : AppCompatActivity() {

    private lateinit var user: FirebaseAuth

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        user = FirebaseAuth.getInstance()

        button.setOnClickListener{
            registerUser()
        }
        
    }

    private fun registerUser()
    {
        val email = emailCadastro.text.toString()
        val senha = senhaCadastro.text.toString()
        val confirmar_senha = confirmarSenhaCadastro.text.toString()

        if(email.isNotEmpty() && senha.isNotEmpty())
        {
            if(senha.equals(confirmar_senha))
            {
                user.createUserWithEmailAndPassword(email,senha)
                    .addOnCompleteListener(CadastroActivity()) {task ->

                        if (task.isSuccessful)
                        {
                            Toast.makeText(this, "Usuario Cadastrado", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, CadastroActivity::class.java))
                            finish()
                        }

                    }
            }
            else
            {
                Toast.makeText(this, "Os dois campos de senha tem que ser iguais", Toast.LENGTH_SHORT).show()
            }

        }
        else
        {
            Toast.makeText(this, "Email e senha Não podem estar vazios", Toast.LENGTH_SHORT).show()
        }
    }
}