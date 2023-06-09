package com.example.pokemoskotlin.main.detailFragment

import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.pokemoskotlin.Pokemon
import com.example.pokemoskotlin.main.detailFragment.PokemonDetailFragmentArgs
import com.example.pokemoskotlin.R
import com.example.pokemoskotlin.databinding.FragmentPokemonDetailBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PokemonDetailFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var hpText: TextView
    private lateinit var attackText: TextView
    private lateinit var defenseText: TextView
    private lateinit var specialAttackText: TextView
    private lateinit var specialDefenseText: TextView
    private lateinit var speedText: TextView
    private lateinit var loadingWheel: ProgressBar
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var descriptionText: TextView

    private val args: PokemonDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = FragmentPokemonDetailBinding.inflate(inflater)

        val pokemon = args.pokemon

        val mediaPlayer = MediaPlayer.create(requireActivity(), pokemon.soundId)

        imageView = view.fragmentDetailImage
        hpText = view.fragmentDetailHp
        attackText = view.fragmentDetailAttack
        defenseText = view.fragmentDetailDefense
        speedText = view.fragmentDetailSpeed
        loadingWheel = view.loadingWheel
        specialAttackText = view.fragmentDetailSpecialAttack
        specialDefenseText = view.fragmentDetailSpecialDefense
        descriptionText  = view.descriptionView

        toolbar = view.detailToolbar

        // Seteo el nombre de mi nueva view acá y mando a llamar setPokemonData con el pokemon que me traigo de navigation
        toolbar.title = pokemon.name.capitalize()
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
            mediaPlayer?.stop()
        }

        // Defino a mi floatingActiongButton:
        floatingActionButton = view.playFab
        floatingActionButton.setOnClickListener {
            mediaPlayer.start()
        }

        // Seteo el resto de los datos de mi fragment con el metodo que tengo abajo.
        setPokemonData(pokemon)

        return view.root
    }

    private fun setPokemonData(pokemon: Pokemon) {

        loadingWheel.visibility = View.VISIBLE
        // Podemos usar this, porque Glide admite fragments:
        Glide.with(this).load(pokemon.imageUrl)
            .listener(object: RequestListener<Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    loadingWheel.visibility = View.GONE
                    imageView.setImageResource(R.drawable.ic_image_not_supported_black)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    loadingWheel.visibility = View.GONE
                    return false
                }
            })
            .error(R.drawable.ic_image_not_supported_black)
            .into(imageView)

        hpText.text = getString(R.string.hp_format, pokemon.hp)
        attackText.text = getString(R.string.attack_format, pokemon.attack)
        defenseText.text = getString(R.string.defense_format, pokemon.defense)
        specialAttackText.text = getString(R.string.special_attack_format, pokemon.specialAttack)
        specialDefenseText.text = getString(R.string.special_defense_format, pokemon.specialDefense)
        speedText.text = getString(R.string.speed_format, pokemon.speed)
        descriptionText.text = getString(R.string.description_format, pokemon.description)


        // val mediaPlayer = MediaPlayer.create(requireActivity(), pokemon.soundId)
        // mediaPlayer.start()
    }
}