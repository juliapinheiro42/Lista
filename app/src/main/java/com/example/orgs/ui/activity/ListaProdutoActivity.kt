package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.R
import com.example.orgs.dao.ProdutosDAO
import com.example.orgs.ui.reciclerview.adapter.ListaProdutosAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaProdutoActivity : ComponentActivity(R.layout.activity_main) {

    private val dao = ProdutosDAO()

    private val adapter = ListaProdutosAdapter(context = this, produtos = dao.buscaTodos())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        extracted()

    }

    override fun onResume() {
        super.onResume()
        configuraFab()
        adapter.atualiza(dao.buscaTodos())
    }

    private fun configuraFab() {
        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener() {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }
    }

    private fun extracted() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycle)
        recyclerView.adapter = adapter
    }


}
