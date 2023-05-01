package com.example.pokemoskotlin.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface PokemonApiService {

    // Como esta función la vamos a ejecutar desde una Coroutine (Hoy en el ViewModel)
    // entonces debe ser una suspend fun.
    @GET("pokemon?limit=151&offset=0")
    suspend fun get151FirstPokemon(): String

    // Como esta función la vamos a ejecutar desde una Coroutine (Hoy en el ViewModel)
    // entonces debe ser una suspend fun.
    @GET("pokemon/{name}")
    suspend fun getPokemonById(@Path("name") name: String): String

}

private var retrofit = Retrofit.Builder()
    .baseUrl("https://pokeapi.co/api/v2/")
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

var service: PokemonApiService = retrofit.create(PokemonApiService::class.java)

