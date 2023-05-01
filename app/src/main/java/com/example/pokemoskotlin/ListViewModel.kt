package com.example.pokemoskotlin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemoskotlin.api.PokemonId
import com.example.pokemoskotlin.api.service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class ListViewModel: ViewModel() {

    // Creo mi variable para la lista de pokemones:
    private var _pokemonList = MutableLiveData<MutableList<Pokemon>>()
    val pokemonList : LiveData<MutableList<Pokemon>>
        get() = _pokemonList

    init {
        viewModelScope.launch {
            _pokemonList.value = reloadPokemon()
        }
    }

    private suspend fun reloadPokemon(): MutableList<Pokemon> {
        return withContext(Dispatchers.IO) {


            val pokemonListPokeApiString = service.get151FirstPokemon()

            // Convierto mi String en un JSON object:
            val pokemonListJsonObject = JSONObject(pokemonListPokeApiString)

            // Armo un array de los pokemon´s id que vienen un mi json:
            val resultJsonArray = pokemonListJsonObject.getJSONArray("results")

            // Armo la lista vacia en la que al final voy a cargar todos mis consultas individuales.
            val pokemonListString = mutableListOf<String>()

            // Itero mi array de pokemons traidos de PokeApi para armar mis objetos:
            for (i in 0 until 3) {
                // Separo al primero de los objetos json traidos desde PokeApi:
                val pokeJsonObject = resultJsonArray[i] as JSONObject

                // Ahora vamos a ir guardando la información del pokemon para luego armar nuestro objeto:
                val name = pokeJsonObject.getString("name")

                // Traigo los datos de mi pokemon pegarle nuevamente a mi API y traer los datos de mi id:
                //val pokeTest = service.getPokemonById(name)

                //Log.d("PokeApi3", pokeTest)
            }

            val pokemonList =  mutableListOf(

                Pokemon(1, "Bulbasaur", 45, 49,
                    49, 45, Pokemon.PokemonType.GRASS, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.klOj-S53SmQJv04FsfsJlgHaEK%26pid%3DApi&f=1&ipt=7697db8767014d122da7e5ac667de9e0323ccc0862d026c6de85e6f1607c1baa&ipo=images"
                    , R.raw.app_src_main_res_raw_bulbasaur),
                Pokemon(
                    2, "Ivysaur", 60, 62,
                    63, 60,  Pokemon.PokemonType.GRASS, "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fimg4.wikia.nocookie.net%2F__cb20140612023052%2Fpokemon%2Fimages%2F9%2F90%2FJimmy_Ivysaur.png&f=1&nofb=1"
                    , R.raw.app_src_main_res_raw_ivysaur),
                Pokemon(
                    3, "Venuasaur", 80, 82,
                    83, 80, Pokemon.PokemonType.GRASS, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fvignette3.wikia.nocookie.net%2Fpokemon%2Fimages%2F5%2F5e%2FBattle_Park_Venusaur.png%2Frevision%2Flatest%3Fcb%3D20151217063847&f=1&nofb=1"
                    , R.raw.app_src_main_res_raw_venusaur),
                Pokemon(
                    4, "Charmander", 39, 52,
                    43, 65, Pokemon.PokemonType.FIRE, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.Jvqxhz2IArmR1XFEtN_fOgHaEy%26pid%3DApi&f=1&ipt=c62d51bda610ff5773946cffde4bef50cd15e774a7d5828f544e157c40cf3615&ipo=images"
                    , R.raw.app_src_main_res_raw_charmander),
                Pokemon(
                    5, "Charmeleon", 58, 64,
                    58, 80, Pokemon.PokemonType.FIRE, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fvignette.wikia.nocookie.net%2Fpoohadventures%2Fimages%2F0%2F06%2F800px-Ash_Charmeleon.png%2Frevision%2Flatest%3Fcb%3D20130815214805&f=1&nofb=1"
                    , R.raw.app_src_main_res_raw_charmeleon),
                Pokemon(
                    6, "Charizard", 78, 84,
                    78, 100, Pokemon.PokemonType.FIRE, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.W4tBCXaevI_C44Kqmb8eYAHaEK%26pid%3DApi&f=1&ipt=64da823989a5850ca9d2885acb25c318b6bd2a1a738576f6b548611939b017fe&ipo=images"
                    , R.raw.app_src_main_res_raw_charizard),
                Pokemon(
                    7, "Squirtle", 44, 48,
                    65, 43, Pokemon.PokemonType.WATER, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwallpapercave.com%2Fwp%2FG8CE8Gu.jpg&f=1&nofb=1"
                    , R.raw.app_src_main_res_raw_squirtle),
                Pokemon(
                    8, "Wartortle", 59, 63,
                    80, 58, Pokemon.PokemonType.WATER, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.5aERdkakEJKzcG70YU-DQAHaEK%26pid%3DApi&f=1&ipt=c53e97395446ab8094af750be008dec3bfd385e68bf72508a14a91abc792a3f5&ipo=images"
                    , R.raw.app_src_main_res_raw_wartortle),
                Pokemon(
                    9, "Blastoise", 79, 83,
                    100, 78, Pokemon.PokemonType.WATER, "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fvignette3.wikia.nocookie.net%2Fpokemon%2Fimages%2F0%2F00%2FBlue_Blastoise_PO.png%2Frevision%2Flatest%3Fcb%3D20151010081320&f=1&nofb=1"
                    , R.raw.app_src_main_res_raw_blastoise),
                Pokemon(
                    25, "Pikachu", 35, 55,
                    40, 90, Pokemon.PokemonType.ELECTRIC, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.4NCQ7a2TKE6hHXnSDIzXPgHaEK%26pid%3DApi&f=1&ipt=cbe4d857bb62a812024f14301a3fccf7c06909397db6203ecac2606f87c7f382&ipo=images"
                    , R.raw.app_src_main_res_raw_pikachu),
                Pokemon(
                    26, "Raichu", 60, 90,
                    55, 110, Pokemon.PokemonType.ELECTRIC, "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.j5RAi62SOM3SmQ01ZQXf_gHaFj%26pid%3DApi&f=1&ipt=47fb2814a745dfdfe046bb59d67b2e7e7b1313e09703c204d275c415d8191196&ipo=images"
                    ,R.raw.app_src_main_res_raw_raichu),
            )

            pokemonList
        }
    }

    private suspend fun parsePokemonListPokeApi(pokemonListPokeApiString: String): MutableList<Pokemon> {
        // Convierto mi String en un JSON object:
        val pokemonListJsonObject = JSONObject(pokemonListPokeApiString)

        // Armo un array de los pokemon´s id que vienen un mi json:
        val resultJsonArray = pokemonListJsonObject.getJSONArray("results")
        Log.d("PokeApi2", resultJsonArray.toString())

        // Armo la lista vacia que al final voy a devolver con mis pokemones parseados:
        val pokemonList = mutableListOf<Pokemon>()

        // Itero mi array de pokemons traidos de PokeApi para armar mis objetos:
        for (i in 0 until 3) {
            // Separo al primero de los objetos json traidos desde PokeApi:
            val pokeJsonObject = resultJsonArray[i] as JSONObject

            // Ahora vamos a ir guardando la información del pokemon para luego armar nuestro objeto:
            val id = pokeJsonObject.getString("name")

            Log.d("PokeName", id)

            // Ejecuto mi Coroutine IO para pegarle nuevamente a mi API y traer los datos de mi id:
            //val pokeTest = getPokemonFromServer(id)

            //Log.d("PokeApi3", pokeTest)
        }

        return pokemonList
    }

    private suspend fun getPokemonFromServer(id: String): String {
        val pokeTest = withContext(Dispatchers.IO) {
            service.getPokemonById(id)
        }
        return pokeTest
    }
}