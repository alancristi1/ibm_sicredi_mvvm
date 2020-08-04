package com.alan.projetopadrao.ui.main.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alan.projetopadrao.data.model.EventItem
import com.alan.projetopadrao.data.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val listEvent = MutableLiveData<List<EventItem>>()

    fun getEventsCoroutines(){
        CoroutineScope(Dispatchers.Main).launch {
            val events = withContext(Dispatchers.Default) {
                repository.getEventsCoroutines()
            }
            listEvent.value = events
        }
    }

    class MainViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory{

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }
    }
}