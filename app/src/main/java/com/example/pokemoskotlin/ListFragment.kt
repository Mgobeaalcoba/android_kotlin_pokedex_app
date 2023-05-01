package com.example.pokemoskotlin

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemoskotlin.api.WorkerUtil
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

        // Llamamos desde aquí al WorkerUtil para que programé la actualización de este fragment:
        WorkerUtil.scheduleSync(requireActivity())

        // Traigo mi ViewModel creado al ListFragment
        // IMPORTANTE: Para pasarle el contexto al ViewModel necesario para instanciar una database debemos usar "requireActivity().application"
        // dado que el applicatión es del Activity y no del Fragment.
        val listViewModel = ViewModelProvider(this  , ListViewModelFactory(requireActivity().application)).get(ListViewModel::class.java)

        // Challenge: Crear el adaptar para nuestro recycler!!!
        val adapter = PokemonAdapter()

        // Observo la actividad en la pokemonList de mi ListViewModel
        listViewModel.pokemonList.observe(requireActivity(), Observer {
            pokemonList ->
            adapter.submitList(pokemonList)
        })

        // Observo la actividad de el status de mi ListViewModel
        listViewModel.status.observe(requireActivity(), Observer {
            // a apiResponseStatus la estoy creando en este lambda function:
                apiResponseStatus ->
            if (apiResponseStatus == ApiResponseStatus.LOADING) {
                view.loadingWheel.visibility = View.VISIBLE
            } else if (apiResponseStatus == ApiResponseStatus.DONE) {
                view.loadingWheel.visibility = View.GONE
            } else if (apiResponseStatus == ApiResponseStatus.ERROR) {
                view.loadingWheel.visibility = View.GONE
            }
        })

        view.pokemonRecycler.adapter = adapter

        adapter.onItemClickListener = {
            pokemonSelectListener.onPokemonSelected(it)
        }

        return view.root
    }
}