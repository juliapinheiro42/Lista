package com.example.orgs.database.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.orgs.model.Produto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun buscaTodos(): Flow<List<Produto>>

    @Query("SELECT * FROM Produto WHERE usuarioId = :usuarioId ")
    fun buscaTodosUsuario(usuarioId: String) : Flow<List<Produto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(vararg produto: Produto)

    @Delete
   suspend fun delete( produto: Produto)

    @Update
   suspend fun update(vararg produto: Produto)


    @Query("SELECT * FROM Produto WHERE id = :id")
    fun buscaPorId(id: Long): Flow<Produto?>

    @Query("SELECT * FROM Produto ORDER BY nome ASC")
    fun buscaTodosOrdenadorPorNomeAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY nome DESC")
    fun buscaTodosOrdenadorPorNomeDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY lista ASC")
    fun buscaTodosOrdenadorPorDescricaoAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY lista DESC")
    fun buscaTodosOrdenadorPorDescricaoDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY preco ASC")
    fun buscaTodosOrdenadorPorValorAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY preco DESC")
    fun buscaTodosOrdenadorPorValorDesc(): List<Produto>
}

