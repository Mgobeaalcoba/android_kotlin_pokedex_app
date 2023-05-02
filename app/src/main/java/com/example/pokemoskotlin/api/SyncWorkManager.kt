package com.example.pokemoskotlin.api

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.pokemoskotlin.main.listFragment.ListRepository
import com.example.pokemoskotlin.database.getDatabase

// Hereda de CoroutineWorker porque traemos los datos en una Coroutine. Sino heredaría de Worker:
class SyncWorkManager(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "SyncWorkManager"
    }

    // Instanciamos nuestra database y nuestro repositorio:
    private val database = getDatabase(appContext)
    private val repository = ListRepository(database)

    override suspend fun doWork(): Result {

        // Uso los metodos de mi repositorio para obtener los datos:
        repository.reloadPokemon()

        return Result.success()
    }
    // Con el SyncWorkManager concluido debo instanciar un singleton object WorkerUtil en el package api
}