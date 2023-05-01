package com.example.pokemoskotlin

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// Creamos el Factory para poder pasar el contexto al ViewModel:
class ListViewModelFactory(private val application: Application):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListViewModel(application) as T
    }
}