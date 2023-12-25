package com.example.producteurapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.producteurapp.model.response.ProducteurResponse
import com.example.producteurapp.model.response.ProduitReponse
import com.example.producteurapp.room.dao.ProduitDao
import com.example.producteurapp.room.dao.ProfilDao

@Database(entities = [ProduitReponse::class, ProducteurResponse::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produitDao(): ProduitDao
    abstract fun profilDao(): ProfilDao


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
                    .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries() // Ne pas autoriser les requêtes sur le thread principal
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Script SQL pour la migration de la version 1 à la version 2
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS profil (" +
                            "email TEXT PRIMARY KEY NOT NULL, " +
                            "nom TEXT, " +
                            "prenom TEXT, " +
                            "adresse TEXT, " +
                            "telephone TEXT, " +
                            "categorie TEXT)"
                )
            }
        }
    }
}

