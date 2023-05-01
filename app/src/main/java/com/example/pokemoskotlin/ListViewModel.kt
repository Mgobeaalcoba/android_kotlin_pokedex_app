package com.example.pokemoskotlin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemoskotlin.api.service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class ListViewModel: ViewModel() {

    // Creo mi variable para la lista de pokemones:
    private var _pokemonList = MutableLiveData<MutableList<Pokemon>>()
    val pokemonList : LiveData<MutableList<Pokemon>>
        get() = _pokemonList

    // Instanciamos un objeto repositorio:
    private val repository = ListRepository()

    init {
        viewModelScope.launch {
            _pokemonList.value = repository.reloadPokemon()
        }
    }
}