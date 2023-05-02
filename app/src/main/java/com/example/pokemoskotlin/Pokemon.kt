package com.example.pokemoskotlin

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("pokemones")
@Parcelize
data class Pokemon (
    @PrimaryKey val id: Int,
    val name: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    val type: String,
    val imageUrl: String,
    val soundId: Int,
    val description: String
): Parcelable
