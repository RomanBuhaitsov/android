package com.example.additional_map_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.additional_map_task.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        val markers = listOf<Marker>(
            Marker(50.0304182,19.9064178, "WMII"),
            Marker(50.0294428,19.9057122, "FAIS"),
            Marker(50.0290757,19.9070532, "WCh"),
            Marker(50.0266401,19.9026346, "WGIG"),
            Marker(50.025923,19.8999335, "WBBIB"),
            Marker(50.0604313,19.9339935, "WP"),
            Marker(50.0610645,19.9316962, "WF"),
            Marker(50.0620363,19.9331501, "UJ CM"),
            Marker(50.0597571,19.9352385, "Uniwersytet Papieski Jana Pawła II"),
            Marker(50.0419272,19.9458767, "Kebab.Niezly."),
        )
        mMap = googleMap

        // Add a marker in UJ and move the camera
        val uj = LatLng(50.0304182,19.9064178)
        for(m:Marker in markers){
            mMap.addMarker(MarkerOptions().position(LatLng(m.lat,m.lng)).title(m.title))
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uj, 13F))
    }

    data class Marker(val lat:Double, val lng: Double, val title: String)
}