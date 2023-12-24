package com.example.producteurapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.producteurapp.model.response.ProduitReponse
import com.example.producteurapp.room.dao.ProduitDao

@Database(entities = [ProduitReponse::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produitDao(): ProduitDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .allowMainThreadQueries() // Ne pas autoriser les requêtes sur le thread principal
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

