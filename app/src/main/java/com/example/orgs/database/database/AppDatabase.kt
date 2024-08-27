package com.example.orgs.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.orgs.database.converter.Converters
import com.example.orgs.database.database.dao.ProdutoDao
import com.example.orgs.model.Produto

@Database(entities = [Produto::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract  fun produtoDao(): ProdutoDao

    companion object {
        fun instancia(context: Context) : AppDatabase{
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "orgs.db"
            )
                .build()
        }
    }
}