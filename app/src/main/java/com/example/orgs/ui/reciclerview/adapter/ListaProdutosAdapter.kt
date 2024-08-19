package com.example.orgs.ui.reciclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.R
import com.example.orgs.model.Produto

class ListaProdutosAdapter(
    private val context: Context,
     produtos: List<Produto>
) : RecyclerView.Adapter<ListaProdutosAdapter.ProdutoViewHolder>() {

    private val produtos = produtos.toMutableList()

    inner class ProdutoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeTextView: TextView = itemView.findViewById(R.id.nome)
        val listaTextView: TextView = itemView.findViewById(R.id.lista)
        val precoTextView: TextView = itemView.findViewById(R.id.preco)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.produto_item, parent, false)
        return ProdutoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val produto = produtos[position]
        holder.nomeTextView.text = produto.nome
        holder.listaTextView.text = produto.lista
        holder.precoTextView.text = produto.preco.toString()
    }

    override fun getItemCount(): Int = produtos.size
    fun atualiza(produtos: List<Produto>) {

        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }
}