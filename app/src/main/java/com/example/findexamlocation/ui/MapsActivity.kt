package com.example.findexamlocation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.findexamlocation.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.findexamlocation.databinding.ActivityMapsBinding
import com.example.findexamlocation.models.Area
import com.example.findexamlocation.utils.Constants
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PolylineOptions

import com.google.android.gms.maps.model.Polyline

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap : GoogleMap
    private lateinit var binding : ActivityMapsBinding
    private lateinit var mArea : Area
    // private lateinit var fusedLocationClient : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.background_gradient
            )
        )

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (intent.hasExtra(Constants.EXTRA_LOCATION)) {
            mArea = intent.getParcelableExtra(Constants.EXTRA_LOCATION)!!
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        mMap.isTrafficEnabled = true

        val destination = LatLng(mArea.lat, mArea.long)
        mMap.addMarker(MarkerOptions().position(destination).title(mArea.name))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(destination))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 15f))

        mMap.addMarker(MarkerOptions().position(getCurrentLocation()).title("You"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(getCurrentLocation(), 15f))

        googleMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .add(
                    getCurrentLocation(),
                    destination,
                )
        )

    }

    private fun getCurrentLocation() : LatLng {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i("Permission", "Permission Granted")
        }
        // Get current location -> Wrong location
        /*
        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                lastLocation = it
                val currentLatLng = LatLng(it.latitude, it.longitude)
                return@addOnSuccessListener
            }
        }
        */
        // Hardcoded current location
        return LatLng(Constants.CURRENT_LAT, Constants.CURRENT_LNG)
    }

    override fun onMarkerClick(p0: Marker): Boolean = false
}