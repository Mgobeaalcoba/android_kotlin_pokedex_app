package com.example.pokemoskotlin.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonApi (
    val id: Int, val name: String, val hp: Int, val attack: Int, val defense: Int,
    val specialAttack: Int, val specialDefense: Int,
    val speed: Int, val type: String, val imageUrl: String, val soundId: Int
): Parcelable
