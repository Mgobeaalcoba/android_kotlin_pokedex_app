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
import java.net.UnknownHostException

// sumó el applicatión para darle el contexto a mi database:
class ListViewModel(application: Application): ViewModel() {

    private val TAG = "Error_internet"

    // Creo mi variable para la lista de pokemones:
    private var _pokemonList = MutableLiveData<MutableList<Pokemon>>()
    val pokemonList : LiveData<MutableList<Pokemon>>
        get() = _pokemonList

    private var _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus>
        get() = _status

    // Instanceo a mi base de datos para luego pasarla al repository:
    private val database = getDatabase(application.applicationContext)

    // Instanciamos un objeto repositorio:
    private val repository = ListRepository(database)

    init {
        viewModelScope.launch {
            try {
                _status.value = ApiResponseStatus.LOADING
                _pokemonList.value = repository.reloadPokemon()
                _status.value = ApiResponseStatus.DONE
            } catch (e: UnknownHostException) {
                _status.value = ApiResponseStatus.NOT_INTERNET_CONEXTION
                Log.d(TAG, "No internet conexion")
            }
        }
    }
}