package com.example.orgs.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.orgs.databinding.FormImageBinding
import com.example.orgs.extensions.Carregar

class FormularioImagemDialog( private val context: Context) {

    fun mostra(urlPadrao: String? = null, quandoImagemCarregada: (Imagem : String) -> Unit){
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val formImage = FormImageBinding.inflate(inflater)

        urlPadrao?.let {
            formImage.imageView3.Carregar(it)
            formImage.urldaimagem.setText(it)
        }

        formImage.carregar.setOnClickListener {
           val url = formImage.urldaimagem.text.toString()
            formImage.imageView3.Carregar(url)
        }


        AlertDialog.Builder(context)
            .setView(formImage.root)
            .setPositiveButton("confirmar") { _, _ ->
               val url = formImage.urldaimagem.text.toString()
            //    binding.imageView2.tentarCarregarImagem(url)
                quandoImagemCarregada(url)
            }
            .setNegativeButton("cancelar") { _, _ ->

            }
            .show()
    }
}