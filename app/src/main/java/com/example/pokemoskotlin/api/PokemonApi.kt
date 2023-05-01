package com.example.pokemoskotlin.api

import com.example.pokemoskotlin.Pokemon

class PokemonApi (
    val id: Long, val name: String, val hp: Int, val attack: Int, val defense: Int,
    val speed: Int, val type: Pokemon.PokemonType, val imageUrl: String
)
