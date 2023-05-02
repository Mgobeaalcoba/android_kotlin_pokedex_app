package com.example.pokemoskotlin.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.pokemoskotlin.main.listFragment.ListFragment
import com.example.pokemoskotlin.main.listFragment.ListFragmentDirections
import com.example.pokemoskotlin.Pokemon
import com.example.pokemoskotlin.R
import com.example.pokemoskotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ListFragment.PokemonSelectListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPokemonSelected(pokemon: Pokemon) {
        findNavController(R.id.main_navigation_container)
            .navigate(ListFragmentDirections.actionListFragmentToPokemonDetailFragment(pokemon))
    }
}