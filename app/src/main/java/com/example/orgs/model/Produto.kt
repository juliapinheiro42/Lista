package com.example.orgs.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Produto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val nome: String,
    val lista: String,
    val preco: BigDecimal,
    val imagem: String? = null
) : Parcelable

