package com.example.pokemoskotlin

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemoskotlin.databinding.FragmentListBinding

class ListFragment : Fragment() {

    interface PokemonSelectListener {
        fun onPokemonSelected(pokemon: Pokemon)
    }

    private lateinit var  pokemonSelectListener: PokemonSelectListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        pokemonSelectListener = try {
            context as PokemonSelectListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement PokemonSelectListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = FragmentListBinding.inflate(inflater)

        val recycler = view.pokemonRecycler

        // Para el layout manager en la activity pasabamos this como contexto. Pero el fragment no tiene contexto por si solo.
        // Debemos pedirle el contexto a la activity
        recycler.layoutManager = LinearLayoutManager(requireActivity())

        // Challenge: Crear el adaptar para nuestro recycler!!!
        val adapter = PokemonAdapter()

        view.pokemonRecycler.adapter = adapter

        adapter.onItemClickListener = {
            pokemonSelectListener.onPokemonSelected(it)
        }

        val pokemonList = mutableListOf(

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

        adapter.submitList(pokemonList)

        return view.root
    }
}