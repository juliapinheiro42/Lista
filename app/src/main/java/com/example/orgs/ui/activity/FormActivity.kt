package com.example.orgs.ui.activity

import com.example.orgs.database.database.AppDatabase
import com.example.orgs.database.database.dao.ProdutoDao
import com.example.orgs.databinding.ActivityFormularioProdutoBinding
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.orgs.extensions.Carregar
import com.example.orgs.model.Produto
import com.example.orgs.ui.dialog.FormularioImagemDialog
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.math.BigDecimal

class FormularioProdutoActivity : UsuarioBaseActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    private var produtoId = 0L
    private val produtoDao: ProdutoDao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }
    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar produto"
        configuraBotaoSalvar()
        binding.imageView2.setOnClickListener {
            FormularioImagemDialog(this)
                .mostra(url) { imagem ->
                    url = imagem
                    binding.imageView2.Carregar(url)
                }
        }
        tentaCarregarProduto()
        lifecycleScope.launch {
            usuario
                .filterNotNull()
                .collect {
                    Log.i("FormularioProduto", "onCreate: $it")
                }
        }
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    override fun onResume() {
        super.onResume()
        tentaBuscarProduto()
    }

    private fun tentaBuscarProduto() {
        lifecycleScope.launch {
            produtoDao.buscaPorId(produtoId).collect {
                it?.let { produtoEncontrado ->
                    title = "Alterar produto"
                    preencheCampos(produtoEncontrado)
                }
            }
        }
    }

    private fun preencheCampos(produto: Produto) {
        url = produto.imagem
        binding.imageView2
            .Carregar(produto.imagem)
        binding.nome
            .setText(produto.nome)
        binding.descricao
            .setText(produto.lista)
        binding.valor
            .setText(produto.preco.toPlainString())
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.salvar

        botaoSalvar.setOnClickListener {
            lifecycleScope.launch {
                usuario.value?.let{ usuario ->
                    val produtoNovo = criaProduto(usuario.id)
                    produtoDao.salva(produtoNovo)
                    finish()
                }
            }
        }
    }

    private fun criaProduto(usuarioId: String): Produto {
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
            id = produtoId,
            nome = nome,
            lista = descricao,
            preco = valor,
            imagem = url,
            usuarioId = usuarioId
        )
    }

}