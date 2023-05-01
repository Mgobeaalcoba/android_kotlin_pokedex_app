package com.example.pokemoskotlin

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemoskotlin.api.service
import com.example.pokemoskotlin.database.getDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

// sumó el applicatión para darle el contexto a mi database:
class ListViewModel(application: Application): ViewModel() {

    // Creo mi variable para la lista de pokemones:
    private var _pokemonList = MutableLiveData<MutableList<Pokemon>>()
    val pokemonList : LiveData<MutableList<Pokemon>>
        get() = _pokemonList

    // Instanceo a mi base de datos para luego pasarla al repository:
    private val database = getDatabase(application.applicationContext)

    // Instanciamos un objeto repositorio:
    private val repository = ListRepository(database)

    init {
        viewModelScope.launch {
            _pokemonList.value = repository.reloadPokemon()
        }
    }
}