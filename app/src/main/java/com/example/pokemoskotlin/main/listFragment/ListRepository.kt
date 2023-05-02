package com.example.pokemoskotlin.main.listFragment

import android.util.Log
import com.example.pokemoskotlin.Pokemon
import com.example.pokemoskotlin.R
import com.example.pokemoskotlin.api.service
import com.example.pokemoskotlin.database.PokemonDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class ListRepository(private val database: PokemonDatabase) {
    // Debe llevar el codigo de conexión con nuestro servidor que sin el repo está en ViewModel:
    // reloadPokemon deja de ser private dado que será usada desde el ViewModel.
    suspend fun reloadPokemon(): MutableList<Pokemon> {
        return withContext(Dispatchers.IO) {

            val pokemonListPokeApiString = service.get151FirstPokemon()

            // Convierto mi String en un JSON object:
            val pokemonListJsonObject = JSONObject(pokemonListPokeApiString)

            // Armo un array de los pokemon´s id que vienen un mi json:
            val resultJsonArray = pokemonListJsonObject.getJSONArray("results")

            // Armo la lista vacia en la que al final voy a cargar todos mis consultas individuales.
            val pokemonListString = mutableListOf<String>()

            // Armo la lista vacia en la que voy a guardar las descripciónes de mis pokemones.
            val pokemonDescriptionListString = mutableListOf<String>()

            // Itero mi array de pokemons traidos de PokeApi para armar mis objetos:
            for (i in 0 until resultJsonArray.length()) {
                // Separo al primero de los objetos json traidos desde PokeApi:
                val pokeJsonObject = resultJsonArray[i] as JSONObject

                // Ahora vamos a ir guardando la información del pokemon para luego armar nuestro objeto:
                val name = pokeJsonObject.getString("name")

                // Traigo los datos de mi pokemon pegarle nuevamente a mi API y traer los datos de mi id:
                val pokemonString = service.getPokemonById(name)

                // Traigo la description de mi pokemon:
                val pokemonDescriptionString = service.getPokemonDescriptionById(name)

                // Imprimo un Log con la descripción para ver que efectivamente llegue
                // Log.d("Description", pokemonDescriptionString) --> La api responde ok

                // Agrego mi pokemonString a una lista de pokemon´s strings:
                pokemonListString.add(pokemonString)

                // Agrego mi pokemonDescriptionString a mi lista de descripciones:
                pokemonDescriptionListString.add(pokemonDescriptionString)
            }

            //Log.d("ListString", pokemonListString.toString())

            // Convierto mi lista de pokemon´s strings a un unico string para parsearlo de forma mas rapida
            //val stringPokemonListString = pokemonListString.joinToString(",")

            // Paso mis lists en formato string juntas a la función de parseo:
            val apiPokemonList = parsePokemonListPokeApi(pokemonListString.toString(), pokemonDescriptionListString.toString())

            // Abro mi database e inserto los datos traidos desde PokeApi:
            database.pokemonDao.insertAll(apiPokemonList)

            // Ya no voy a enviar entonces mis datos a pintar desde la API:
            // apiPokemonList

            // Sino que los voy a mandar a pintar desde la database:
            database.pokemonDao.getPokemones()
        }
    }

    suspend fun reloadPokemonFromDb(): MutableList<Pokemon> {
        return withContext(Dispatchers.IO) {
            database.pokemonDao.getPokemones()
        }
    }

    // parsePokemonListPokeApi sigue siendo privada dado que es llamada solo desde acá por la func de arriba:
    private fun parsePokemonListPokeApi(pokemonListPokeApiString: String, pokemonDescriptionListString: String): MutableList<Pokemon> {
        // Convierto mi primer String en un JSON Array:
        val pokemonListJsonArray = JSONArray(pokemonListPokeApiString)

        // Convierto mi segundo String en un JSON Array:
        val pokemonDescriptionListJsonArray = JSONArray(pokemonDescriptionListString)

        // Imprimo mi array por LogCat para ver si está como quiero:
        // Log.d("PokeApi2", pokemonListJsonArray.toString())

        // Armo la lista vacia que al final voy a devolver con mis pokemones parseados:
        val pokemonList = mutableListOf<Pokemon>()

        // Itero mi array de pokemons traidos de PokeApi para armar mis objetos:
        for (i in 0 until pokemonListJsonArray.length()) {
            // Separo al primero de los objetos json traidos desde PokeApi:
            val pokeJsonObject = pokemonListJsonArray[i] as JSONObject
            val pokeDescriptionJsonObject = pokemonDescriptionListJsonArray[i] as JSONObject

            //Log.d("PokeJsonObjetc", pokeJsonObject.toString())

            // Ahora vamos a ir guardando la información del pokemon para luego armar nuestro objeto:
            val name = pokeJsonObject.getString("name")
            val id = pokeJsonObject.getInt("id")
            val statsArray = pokeJsonObject.getJSONArray("stats")
            val hp = statsArray.getJSONObject(0).getInt("base_stat")
            val attack = statsArray.getJSONObject(1).getInt("base_stat")
            val defense = statsArray.getJSONObject(2).getInt("base_stat")
            val specialAttack = statsArray.getJSONObject(3).getInt("base_stat")
            val specialDefense = statsArray.getJSONObject(4).getInt("base_stat")
            val speed = statsArray.getJSONObject(5).getInt("base_stat")
            val typesArray = pokeJsonObject.getJSONArray("types")
            val type = typesArray.getJSONObject(0).getJSONObject("type").getString("name")
            val spritesObject = pokeJsonObject.getJSONObject("sprites")
            val otherObject = spritesObject.getJSONObject("other")
            val officialArtwork = otherObject.getJSONObject("official-artwork")
            val imageUrl = officialArtwork.getString("front_default")
            val soundId = R.raw.pokemon_battle
            val formDescriptions = pokeDescriptionJsonObject.getJSONArray("flavor_text_entries")
            val description = formDescriptions.getJSONObject(0).getString("flavor_text")

            // Armamos nuestro objeto PokemonApi que es una clase transitoria:
            val pokemon = Pokemon(id,name,hp,attack,defense,specialAttack,specialDefense,speed,type,imageUrl,soundId,description)

            // Agrego el pokemon creado a mi lista:
            pokemonList.add(pokemon)


            Log.d("PokemonApiObject", pokemon.toString())
        }

        return pokemonList
    }

}