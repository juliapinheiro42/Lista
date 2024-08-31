package com.example.orgs.database.database.dao

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface Usuario {

    @Insert
    suspend fun salva(usuario: Usuario)
}