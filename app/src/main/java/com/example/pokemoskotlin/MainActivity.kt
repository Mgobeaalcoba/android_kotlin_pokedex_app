package com.example.pokemoskotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.findFragment
import com.example.pokemoskotlin.databinding.ActivityMainBinding
import com.example.pokemoskotlin.databinding.FragmentDetailBinding

class MainActivity : AppCompatActivity(), ListFragment.PokemonSelectListener {

    private lateinit var detailFragment: DetailFragment
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailFragment = supportFragmentManager.findFragmentById(R.id.detail_fragment) as DetailFragment
    }

    override fun onPokemonSelected(pokemon: Pokemon) {
        detailFragment.setPokemonData(pokemon)
    }
}