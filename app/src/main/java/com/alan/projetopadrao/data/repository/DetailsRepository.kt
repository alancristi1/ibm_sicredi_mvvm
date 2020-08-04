package com.alan.projetopadrao.data.repository

import com.alan.projetopadrao.data.api.RetrofitInit
import com.alan.projetopadrao.data.model.EventItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class DetailsRepository : KoinComponent {

    private val retrofit : RetrofitInit by inject()

    suspend fun getDetailsCoroutines(id: String): EventItem {

        return withContext(Dispatchers.Default){
            retrofit.apiService.getDetailsEvent(id)
        }
    }

    suspend fun setCheckinEvent(id : String, name : String, email : String){
        retrofit.apiService.setCheckin(id, name, email)

    }
}