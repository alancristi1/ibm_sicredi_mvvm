package com.alan.projetopadrao.ui.details.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alan.projetopadrao.R
import com.alan.projetopadrao.data.model.EventItem
import com.alan.projetopadrao.data.repository.DetailsRepository
import com.alan.projetopadrao.ui.details.viewModel.DetailsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var setPosition : LatLng
    private var latitude = 0.0
    private var longitude = 0.0
    private lateinit var details : EventItem
    lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val id  = intent.getStringExtra("id_event")
        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)

        setPosition = LatLng(latitude, longitude)

        viewModel = ViewModelProvider(this,
            DetailsViewModel.DetailsViewModelFactory(DetailsRepository())).get(DetailsViewModel::class.java)

        viewModel.detailsObj.observe(this, Observer { details ->
            renderActivity(details)
        })

        viewModel.getDetailsCoroutines(id)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap =  googleMap
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(setPosition, 15F))
    }

    fun openDialog(events: EventItem) {
        val builder = AlertDialog.Builder(this).create()
        val inflater = layoutInflater
        val dialogLayout: View = inflater.inflate(R.layout.custom_checkin_layout, null)

        val inputName = dialogLayout.findViewById<EditText>(R.id.inputName)
        val inputEmail = dialogLayout.findViewById<EditText>(R.id.inputEmail)

        val name = inputName.text
        val email = inputEmail.text
        val btn = dialogLayout.findViewById<Button>(R.id.buttonCheckin)
        btn.setOnClickListener {
            if(name.isEmpty() && email.isEmpty()){
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_LONG).show()

            }else{
                viewModel.setCheckinEvent(events.id, name.toString(), email.toString())
                builder.dismiss()
            }
        }

        builder.setView(dialogLayout)
        builder.show()
    }

    private fun renderActivity(details: EventItem) {

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = details.title

        Picasso.get().load(details.image).into(imgDetails, object : Callback {
            override fun onSuccess() {}

            override fun onError(e: Exception?) {

                Picasso.get()
                    .load(R.drawable.default_image)
                    .into(imgDetails)
            }
        })

        mMap.addMarker(MarkerOptions().position(setPosition).title(details.title))

        txtDescribeDetails.text = details.description

        txtPriceDetails.text = details.price.toString()

        val discount = details.cupons[0].discount.toDouble().div(100)
        val apply = details.price.times(discount)

        val decimal = BigDecimal(details.price - apply).setScale(2, RoundingMode.HALF_EVEN)

        txtPriceWithDiscount.text = "R$ $decimal"

        txtDataDetails.text = "Data: " + convertDate(details.date)

        btn_checkin.setOnClickListener {
            openDialog(details)
        }
    }

    private fun convertDate(time : Long) : String{
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return format.format(time)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}