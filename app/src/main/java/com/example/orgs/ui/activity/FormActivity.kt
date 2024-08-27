package com.example.orgs.ui.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.database.database.AppDatabase
import com.example.orgs.databinding.ActivityFormularioProdutoBinding
import com.example.orgs.extensions.Carregar
import com.example.orgs.model.Produto
import com.example.orgs.ui.dialog.FormularioImagemDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }

    private val produtoDao by lazy {
        AppDatabase.instancia(this).produtoDao()

    }

    private var url: String? = null
    private var idProduto = 0L
    private var scope = CoroutineScope(Dispatchers.IO)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoSalvar()
        title = "Formulário de produto"


        binding.imageView2.setOnClickListener {

            FormularioImagemDialog(this).mostra(url) { imagem ->
                url = imagem
                binding.imageView2.Carregar(url)
            }
        }
        tentarBuscarProdutos()

    }

    override fun onResume() {
        super.onResume()
        scope.launch {
        produtoDao.buscaPorId(idProduto)?.let {
            withContext(Dispatchers.Main){
                preenchaCampos(it)
            }
        }
        }
    }

    private fun tentarBuscarProdutos() {

        idProduto = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }



    private fun preenchaCampos(produtoCarregado: Produto) {
        title = "Editar produto"
        url = produtoCarregado.imagem
        binding.imageView2.Carregar(produtoCarregado.imagem)
        binding.nome.setText(produtoCarregado.nome)
        binding.descricao.setText(produtoCarregado.lista)
        binding.valor.setText(produtoCarregado.preco.toPlainString())
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.salvar

        botaoSalvar.setOnClickListener {
            scope.launch {
                val produtoNovo = criaProduto()
                produtoDao.salva(produtoNovo)
                finish()
            }
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = binding.nome
        val nome = campoNome.text.toString()
        val campoDescricao = binding.descricao
        val descricao = campoDescricao.text.toString()
        val campoValor = binding.valor
        val valorEmTexto = campoValor.text.toString()
        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            id = idProduto,
            nome = nome,
            lista = descricao,
            preco = valor,
            imagem = url
        )
    }

}