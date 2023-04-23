package com.example.pokemoskotlin

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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.pokemoskotlin.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var hpText: TextView
    private lateinit var attackText: TextView
    private lateinit var defenseText: TextView
    private lateinit var speedText: TextView
    private lateinit var loadingWheel: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = FragmentDetailBinding.inflate(inflater)

        imageView = view.fragmentDetailImage
        hpText = view.fragmentDetailHp
        attackText = view.fragmentDetailAttack
        defenseText = view.fragmentDetailDefense
        speedText = view.fragmentDetailSpeed
        loadingWheel = view.loadingWheel

        return view.root
    }

    fun setPokemonData(pokemon: Pokemon) {

        loadingWheel.visibility = View.VISIBLE
        // Podemos usar this, porque Glide admite fragments:
        Glide.with(this).load(pokemon.imageUrl)
            .listener(object: RequestListener<Drawable>{

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
        speedText.text = getString(R.string.speed_format, pokemon.speed)

        val mediaPlayer = MediaPlayer.create(requireActivity(), pokemon.soundId)
        mediaPlayer.start()
    }
}