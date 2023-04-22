package com.example.pokemoskotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
    ): PokemonAdapter.PokemonViewHolder {
        val binding = PokemonListItemBinding.inflate(LayoutInflater.from(parent.context))
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonAdapter.PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(pokemon)
    }

    inner class PokemonViewHolder(private val binding: PokemonListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            // pokemon_id / pokemon_name / pokemon_type_image
            binding.pokemonId.text = pokemon.id.toString()
            binding.pokemonName.text = pokemon.name

            val imageId = when(pokemon.type) {
                Pokemon.PokemonType.GRASS -> R.drawable.grass_icon
                Pokemon.PokemonType.WATER -> R.drawable.water_icon
                Pokemon.PokemonType.FIRE -> R.drawable.fire_icon
                Pokemon.PokemonType.FIGHTER -> R.drawable.fight_icon
                Pokemon.PokemonType.ELECTRIC -> R.drawable.electric_icon
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