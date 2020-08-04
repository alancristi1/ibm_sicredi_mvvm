package com.alan.projetopadrao.ui.main.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alan.projetopadrao.R
import com.alan.projetopadrao.data.repository.MainRepository
import com.alan.projetopadrao.ui.main.adapter.MainAdapter
import com.alan.projetopadrao.ui.main.viewModel.MainViewModel
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowHomeEnabled(false)
        supportActionBar!!.title = "Eventos"

        showLoading(true)

        viewModel = ViewModelProvider(this,
            MainViewModel.MainViewModelFactory(MainRepository())).get(MainViewModel::class.java)

        swipeHome.setOnRefreshListener {
            viewModel.getEventsCoroutines()
            showLoading(false)
        }

        viewModel.listEvent.observe(this, Observer { events ->
            list_events.layoutManager = LinearLayoutManager(this)
            list_events.adapter = MainAdapter(events)
        })

        viewModel.getEventsCoroutines()
        showLoading(false)
    }

    private fun showLoading(enable: Boolean) {
        if(enable){
            loadingLayout.visibility = View.VISIBLE
        }else{
            loadingLayout.visibility = View.GONE
            swipeHome.isRefreshing = false
        }
    }
}