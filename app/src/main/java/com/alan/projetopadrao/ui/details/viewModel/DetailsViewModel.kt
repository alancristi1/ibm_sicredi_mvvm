package com.alan.projetopadrao.ui.details.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alan.projetopadrao.data.model.EventItem
import com.alan.projetopadrao.data.repository.DetailsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(private val repository: DetailsRepository) : ViewModel() {

    val detailsObj = MutableLiveData<EventItem>()

    fun getDetailsCoroutines(id : String){

        CoroutineScope(Dispatchers.Main).launch {
            val details = withContext(Dispatchers.Default){
                repository.getDetailsCoroutines(id)
            }

            detailsObj.value = details
        }
    }

    fun setCheckinEvent(id : String, name : String, email : String){
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Default){
                repository.setCheckinEvent(id, name, email)
            }
        }
    }

    class DetailsViewModelFactory(private val repository: DetailsRepository) : ViewModelProvider.Factory{

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetailsViewModel(repository) as T
        }
    }
}