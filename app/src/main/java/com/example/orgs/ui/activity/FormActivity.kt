package com.example.orgs.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.orgs.R
import com.example.orgs.dao.ProdutosDAO
import com.example.orgs.model.Produto
import com.example.orgs.ui.activity.ui.theme.OrgsTheme
import java.math.BigDecimal

class FormActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_produto)

        val button_save = findViewById<Button>(R.id.salvar)
        button_save.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val novoProduto = criaProduto()


                Log.i("FormActivity", "onCreate: $novoProduto")
                val dao = ProdutosDAO()

                dao.add(novoProduto)
                finish()
            }

            private fun criaProduto(): Produto {
                val campoNome = findViewById<EditText>(R.id.nome)
                val nome = campoNome.text.toString()
                val campoDescricao = findViewById<EditText>(R.id.descricao)
                val descricao = campoDescricao.text.toString()
                val campoValor = findViewById<EditText>(R.id.valor)
                val valorEmTexto = campoValor.text.toString()

                val valor = if (valorEmTexto.isBlank()) {
                    BigDecimal.ZERO
                } else {
                    BigDecimal(valorEmTexto)
                }
                val novoProduto = Produto(
                    nome = nome,
                    lista = descricao,
                    preco = valor
                )
                return novoProduto
            }
        })

    }
}