package com.example.orgs.ui.reciclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.databinding.ProdutoItemBinding
import com.example.orgs.extensions.Carregar
import com.example.orgs.extensions.formataParaMoedaBrasileira
import com.example.orgs.model.Produto


class ListaProdutosAdapter(
    private val context: Context,
    produtos: List<Produto> = emptyList(),
    var quandoClicaNoItem: (produto: Produto) -> Unit = {}
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()

    inner class ViewHolder(private val binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun vincula(produto: Produto) {
            with(binding) {
                // Atualiza os dados do produto
                nome.text = produto.nome
                lista.text = produto.lista
                preco.text = produto.preco.formataParaMoedaBrasileira()

                // Atualiza a visibilidade e carrega a imagem
                if (produto.imagem != null) {
                    imageView.visibility = View.VISIBLE
                    imageView.Carregar(produto.imagem)
                } else {
                    imageView.visibility = View.GONE
                }

                // Configura o clique do item
                root.setOnClickListener {
                    quandoClicaNoItem(produto)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ProdutoItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto)
    }

    override fun getItemCount(): Int = produtos.size

    fun atualiza(novosProdutos: List<Produto>) {
        // Atualiza a lista de produtos usando DiffUtil
        val diffCallback = ProdutosDiffCallback(produtos, novosProdutos)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        produtos.clear()
        produtos.addAll(novosProdutos)
        diffResult.dispatchUpdatesTo(this)
    }
}

// Classe DiffUtil.Callback para atualizar a lista de forma eficiente
class ProdutosDiffCallback(
    private val oldList: List<Produto>,
    private val newList: List<Produto>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}