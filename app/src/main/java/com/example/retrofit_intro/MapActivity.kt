package com.example.retrofit_intro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofit_intro.R
import com.example.retrofit_intro.databinding.ActivityMainBinding
import com.example.retrofit_intro.databinding.ActivityMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.properties.Delegates

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
//        binding = ActivityMapBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this@MapActivity)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val bundle = intent.extras
        var latitude by Delegates.notNull<Double>()
        var longitude by Delegates.notNull<Double>()

        if(bundle != null){
            latitude = bundle.getDouble("lat")
            longitude = bundle.getDouble("lon")
        }

        val coordinates = LatLng(latitude, longitude)

        googleMap.addMarker(
            MarkerOptions()
                .position(coordinates)
                .title("Marker")
        )

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates))
    }

    //google maps does the REST api key call within the getMapAsync function


}