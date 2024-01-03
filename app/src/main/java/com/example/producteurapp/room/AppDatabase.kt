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

@Database(entities = [ProduitReponse::class, ProducteurResponse::class], version = 1)
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
//                    .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries() // Ne pas autoriser les requêtes sur le thread principal
                    .build()
                INSTANCE = instance
                instance
            }
        }

//        private val MIGRATION_1_2 = object : Migration(2, 3) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                // Script SQL pour la migration de la version 1 à la version 2
//                database.execSQL("CREATE TABLE IF NOT EXISTS ProduitReponse_new (id INTEGER NOT NULL, nom TEXT, prix REAL, description TEXT, quantite INTEGER, emailProducteur TEXT, date_publication TEXT, categorie TEXT, isDelete INTEGER NOT NULL DEFAULT 0, PRIMARY KEY(id))")
//                database.execSQL("INSERT INTO ProduitReponse_new (id, nom, prix, description, quantite, emailProducteur, date_publication, categorie, isDelete) SELECT id, nom, prix, description, quantite, emailProducteur, date_publication, categorie, isDelete FROM produits")
//                database.execSQL("DROP TABLE produits")
//                database.execSQL("ALTER TABLE ProduitReponse_new RENAME TO produits")
//
////                database.execSQL(
////                    "CREATE TABLE IF NOT EXISTS profil (" +
////                            "email TEXT PRIMARY KEY NOT NULL, " +
////                            "nom TEXT, " +
////                            "prenom TEXT, " +
////                            "adresse TEXT, " +
////                            "telephone TEXT, " +
////                            "categorie TEXT)"
////                )
//            }
//        }
    }
}

