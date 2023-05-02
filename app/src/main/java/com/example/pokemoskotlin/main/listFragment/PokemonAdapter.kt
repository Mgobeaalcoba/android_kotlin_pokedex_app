package com.example.pokemoskotlin.main.listFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemoskotlin.Pokemon
import com.example.pokemoskotlin.R
import com.example.pokemoskotlin.databinding.PokemonListItemBinding

class PokemonAdapter: ListAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(
    DiffCallback
) {

    companion object DiffCallback: DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }
    }

    // Lambda para escuchar los clicks sobre los elementos de mi lista:
    lateinit var  onItemClickListener: (Pokemon) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        val binding = PokemonListItemBinding.inflate(LayoutInflater.from(parent.context))
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(pokemon)
    }

    inner class PokemonViewHolder(private val binding: PokemonListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            // pokemon_id / pokemon_name / pokemon_type_image
            binding.pokemonId.text = pokemon.id.toString()
            binding.pokemonName.text = pokemon.name.capitalize()

            val imageId = when(pokemon.type) {
                "grass" -> R.drawable.grass_icon
                "water" -> R.drawable.water_icon
                "fire" -> R.drawable.fire_icon
                "fighting" -> R.drawable.fight_icon
                "electric" -> R.drawable.electric_icon
                "normal" -> R.drawable.normal
                "flying" -> R.drawable.flying
                "poison" -> R.drawable.poison
                "ground" -> R.drawable.ground
                "rock" -> R.drawable.rock
                "bug" -> R.drawable.bug
                "ghost" -> R.drawable.ghost
                "steel" -> R.drawable.steel
                "psychic" -> R.drawable.phychic
                "ice" -> R.drawable.ice
                "dragon" -> R.drawable.dragon
                "dark" -> R.drawable.dark
                "fairy" -> R.drawable.fairy
                else -> R.drawable.normal
            }

            binding.pokemonTypeImage.setImageResource(imageId)

            binding.root.setOnClickListener {
                if (::onItemClickListener.isInitialized) {
                    onItemClickListener(pokemon)
                }
            }

            binding.executePendingBindings()
        }
    }
}