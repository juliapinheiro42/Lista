package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.orgs.R
import com.example.orgs.database.database.AppDatabase
import com.example.orgs.databinding.ActivityDetalhesProdutoBinding
import com.example.orgs.extensions.Carregar
import com.example.orgs.extensions.formataParaMoedaBrasileira
import com.example.orgs.model.Produto
import kotlinx.coroutines.launch

class DetalhesProdutoActivity : AppCompatActivity() {


    private var idProduto: Long = 0L
    private  var ProdutoCarregado: Produto? = null
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    private val produtoDao by lazy {
         AppDatabase.instancia(this).produtoDao()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("DetalhesProdutoActivity", "onCreate chamado")
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            produtoDao.buscaPorId(idProduto).collect { produtoEncontrado ->
                ProdutoCarregado = produtoEncontrado
                ProdutoCarregado?.let {
                    preencheCampos(it)
                } ?: finish()
            }
        }

    }

    private fun tentaCarregarProduto() {
        // Tenta obter o produto a partir do Intent
        idProduto = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)



    }

    private fun preencheCampos(produtoCarregado: Produto) {
        with(binding) {
            // Verifica se a imagem está presente antes de tentar carregá-la
            activityDetalhesProdutoImagem.Carregar(produtoCarregado.imagem ?: "")
            activityDetalhesProdutoNome.text = produtoCarregado.nome
            activityDetalhesProdutoDescricao.text = produtoCarregado.lista
            activityDetalhesProdutoValor.text = produtoCarregado.preco.formataParaMoedaBrasileira()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
          when (item.itemId) {
              R.id.menu_remover -> {
                  ProdutoCarregado?.let {
                      lifecycleScope.launch {
                          produtoDao.delete(it)
                          finish()
                      }
                  }

              }

              R.id.menu_editar -> {

                  Intent(this, FormularioProdutoActivity::class.java).apply {
                      putExtra(CHAVE_PRODUTO_ID, idProduto)
                      startActivity(this)
                  }
              }
          }

        return super.onOptionsItemSelected(item)
    }
}