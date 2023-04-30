package com.example.pokemoskotlin.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header


interface PokemonApiService {

    // Aquí podría agregar todos tipos de request que quisiera. En este caso solo voy a usar el GET.
    @GET("pokemon?limit=151&offset=0")
    suspend fun get151FirstPokemon(): PokemonListJsonResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Header("id") id: String): PokemonJsonResponse

}

private var retrofit = Retrofit.Builder()
    .baseUrl("https://pokeapi.co/api/v2/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

var service: PokemonApiService = retrofit.create(PokemonApiService::class.java)

