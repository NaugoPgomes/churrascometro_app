package com.naugo.churrascometro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException
import kotlin.math.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_calcular.setOnClickListener(this)
    }

    override fun onClick(view: View)
    {
        val id = view.id
        if (id == R.id.button_calcular) {
            calcular()
        }
    }

    private fun calcular()
    {
        if(validado())
        {
            try {

                var adutosQueBebem = adutos.text.toString().toFloat()
                var adutosNaoBebem = aduto_n_bebem.text.toString().toFloat()
                var criancas = criancas.text.toString().toFloat()
                var duracao = duracao.text.toString().toFloat()
                var adutos = adutosQueBebem + adutosNaoBebem

                var totalDecarne = (carne(duracao) * adutos + (carne(duracao) / 2 * criancas)) / 1000
                var totalDeBeer = (beer(duracao) * adutosQueBebem) / 355
                var totalDeRefri = (refri(duracao) * adutosNaoBebem + (refri(duracao) / 2 * criancas)) / 2000


                valor_carne.text = "${"%.2f".format(totalDecarne)}"
                valor_beer.text = "${"%.0f".format(Math.ceil(totalDeBeer.toDouble()))}"
                valor_refri.text = "${"%.0f".format(Math.ceil(totalDeRefri.toDouble()))}"
            }catch (nfe: NumberFormatException){
                Toast.makeText(this, "Informe valores validos", Toast.LENGTH_LONG).show()
            }
        }
        else
        {
            if(duracao.text.toString() == "0")
            {
                Toast.makeText(this, "O horario não pode ser 0", Toast.LENGTH_LONG).show()
            }
            else
            {
                Toast.makeText(this, "Nenhum campos pode estar vazio", Toast.LENGTH_LONG).show()
            }

        }

    }

    fun carne(duracao: Float): Int
    {
        if(duracao >= 6)
        {
            return 550
        }
        else
        {
            return 400
        }
    }

    fun beer(duracao: Float): Int
    {
        if(duracao >= 6)
        {
            return 2000
        }
        else
        {
            return 1200
        }
    }

    fun refri(duracao: Float): Int
    {
        if(duracao >= 6)
        {
            return 1500
        }
        else
        {
            return 1000
        }
    }

    private fun validado(): Boolean {

        return (adutos.text.toString() != "" && aduto_n_bebem.text.toString() != ""
                && criancas.text.toString() != "" && duracao.text.toString() != "" && duracao.text.toString() != "0")
    }
}