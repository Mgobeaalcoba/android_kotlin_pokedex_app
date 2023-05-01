package com.example.pokemoskotlin.api

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object WorkerUtil {
    fun scheduleSync(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // Ponemos como condición que esté conectado a internet
            .setRequiresBatteryNotLow(true) // También que no tenga batería baja
            .build()

        val syncRequest =
            // Enviamos la request para actualizar los datos cada 7 días. Dado que son datos cuasi estaticos:
            PeriodicWorkRequestBuilder<SyncWorkManager>(7, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            SyncWorkManager.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, syncRequest
        )
    }
}