package com.alan.projetopadrao.data.repository

import com.alan.projetopadrao.data.api.RetrofitInit
import com.alan.projetopadrao.data.model.EventItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject


class MainRepository : KoinComponent{

    private val retrofit : RetrofitInit by inject()

    suspend fun getEventsCoroutines() : List<EventItem> {
        return withContext(Dispatchers.Default) {
            retrofit.apiService.getEvents()
        }
    }
}