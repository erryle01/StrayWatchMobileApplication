package com.example.loginsystem

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapTest : AppCompatActivity(), OnMapReadyCallback {

    private var mGoogleMap:GoogleMap? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_map01)

        val mapFragment = supportFragmentManager.findFragmentById(R.layout.test_map01) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap){
        mGoogleMap = googleMap
    }

}