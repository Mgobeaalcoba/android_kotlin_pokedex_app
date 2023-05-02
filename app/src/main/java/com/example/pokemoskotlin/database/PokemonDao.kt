package com.example.pokemoskotlin.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pokemoskotlin.Pokemon


@Dao
interface PokemonDao {

    // Método para insertar pokemones:
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemonList: MutableList<Pokemon>)

    // Método para traer de nuestra base de datos los pokemones:
    @Query("SELECT * FROM pokemones")
    fun getPokemones(): MutableList<Pokemon>

    // Metodo para actualizar datos de mi base:
    @Update
    fun updatePokemon(vararg pokemon: Pokemon)

    // Metodo para eliminar pokemones de mi base:
    @Delete
    fun deletePokemon(vararg pokemon: Pokemon)

}