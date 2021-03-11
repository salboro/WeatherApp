package com.example.weatherapp.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import java.util.concurrent.TimeUnit


class WeatherAppLocationService(
    private val context: Context,
    private val activity: Activity
) {

    private val REQUEST_LOCATION_PERMISSION = 1

    private var isPermissionChecked: Boolean = false

    private lateinit var locationRequest: LocationRequest

    private lateinit var locationCallback: LocationCallback

    @SuppressLint("VisibleForTests")
    private var locationService: FusedLocationProviderClient = FusedLocationProviderClient(context)


    fun getLastLocation(setLocation: (location: Location?) -> Unit) {
        checkPermission()

        if (isPermissionChecked) {
            if (isLocationEnable()) {
                locationService.lastLocation.addOnSuccessListener { location ->
                    Log.i("asfa", "1")
                    if (location != null) {
                        setLocation(location)
                        requestNewLocation(setLocation)
                    } else {
                        requestNewLocation(setLocation)
                    }
                }.addOnCanceledListener {
                    Log.i("asfa", "2")
                }.addOnFailureListener {
                    Log.i("asfa", "3")
                }.addOnCompleteListener {
                    Log.i("asfa", "4")
                }
            } else {
                Toast.makeText(
                    context,
                    "You have trouble with connection.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun requestNewLocation(setLocation: (location: Location?) -> Unit) {
        locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(5)
            fastestInterval = TimeUnit.SECONDS.toMillis(0)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            numUpdates = 1
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                checkPermission()
                setLocation(locationResult.lastLocation)
            }
        }

        locationService.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

    }


    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            enablePermission()
        } else {
            isPermissionChecked = true
        }
    }

    private fun isLocationEnable(): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || (locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        ))
    }

    private fun isPermissionGranted(): Boolean {
        return (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }


    fun enablePermission() {
        if (isPermissionGranted()) {
            isPermissionChecked = true
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf<String>(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }
}