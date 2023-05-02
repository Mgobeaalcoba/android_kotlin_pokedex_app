package com.example.pokemoskotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokemoskotlin.Pokemon

@Database(entities = [Pokemon::class], version = 1)
abstract class PokemonDatabase: RoomDatabase() {

    // Gracias a esta clase abstracta es que vamos a poder usar el Dao:
    abstract val pokemonDao: PokemonDao

}

private lateinit var INSTANCE: PokemonDatabase

fun getDatabase(context: Context): PokemonDatabase {
    // Sincronizo a todos los hilos o threads para que sepan que estoy usando la database:
    synchronized(PokemonDatabase::class.java) {
        // Si no se instanció la base entonces lo hago. Si ya está instancia entonces solo la retorno.
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                PokemonDatabase::class.java,
                // nombre de la database. No confundir con el nombre de la tabla
                "pokemonapp"
            ).build()
        }
        return INSTANCE
    }
}

