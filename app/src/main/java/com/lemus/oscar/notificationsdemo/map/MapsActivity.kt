package com.lemus.oscar.notificationsdemo.map

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lemus.oscar.notificationsdemo.R
import android.content.Context.LOCATION_SERVICE
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.location.LocationListener
import android.util.Log
import android.view.View
import android.widget.*
import java.security.AccessController
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.Circle




class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var current: LatLng
    private lateinit var option: Spinner

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val radius: EditText = findViewById(R.id.editText)
        val button: Button = findViewById(R.id.button2)
        button.setOnClickListener {
            if (!radius.text.isNullOrEmpty()) {
                mMap.clear()
                mMap.addMarker(MarkerOptions().position(current).title("Marker in Sydney"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 14F))
                var distance = radius.text.toString().toDouble()
                if (option.selectedItemPosition == 1)
                    distance *= 1000
                mMap.addCircle(CircleOptions()
                        .center(current)
                        .radius(distance)
                        .strokeColor(Color.RED)
                        .fillColor(Color.TRANSPARENT))
            }
        }

        option = findViewById(R.id.spinner)
        val options = arrayOf("m", "Km")
        option.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options)

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
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        current = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(current).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current))

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val locationListener = MyLocationListener(mMap, this)
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10.toFloat(), locationListener)
    }

    private class MyLocationListener(val mMap: GoogleMap, var activity: MapsActivity):LocationListener {



        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

        }

        override fun onProviderEnabled(p0: String?) {

        }

        override fun onProviderDisabled(p0: String?) {

        }

        override fun onLocationChanged(p0: Location?) {
            mMap.clear()

            Log.d("Location", (p0?.latitude ?: "None").toString() + ":" + (p0?.longitude ?: "None").toString())
            activity.current = LatLng(p0?.latitude?:-34.0, p0?.longitude?:151.0)
            mMap.addMarker(MarkerOptions().position(activity.current).title("Marker in Sydney"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(activity.current))

            val circle = mMap.addCircle(CircleOptions()
                    .center(activity.current)
                    .radius(1000.0)
                    .strokeColor(Color.RED)
                    .fillColor(Color.TRANSPARENT))

        }

    }
}
