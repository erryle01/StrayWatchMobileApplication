package com.example.loginsystem

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.loginsystem.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val db = FirebaseFirestore.getInstance()
    private var markersMap = HashMap<Marker, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Set click listener for adding marker on map
        mMap.setOnMapClickListener(this)

        // Load pinpoints from Firestore
        loadPinpointsFromFirestore()
    }

    override fun onMapClick(point: LatLng) {
        showMarkerInputDialog(point)
    }

    @SuppressLint("MissingInflatedId")
    private fun showMarkerInputDialog(point: LatLng) {
        // Dialog to input marker details
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialogue_marker_input, null)
        dialogBuilder.setView(dialogView)

        val etTitle = dialogView.findViewById<EditText>(R.id.et_title)
        val etSnippet = dialogView.findViewById<EditText>(R.id.et_snippet)

        dialogBuilder.setTitle("Add Marker")
        dialogBuilder.setMessage("Enter marker details:")
        dialogBuilder.setPositiveButton("Add") { dialog, which ->
            val title = etTitle.text.toString()
            val snippet = etSnippet.text.toString()
            addMarker(point, title, snippet)
            savePinpointToFirestore(point, title, snippet)
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun addMarker(point: LatLng, title: String, snippet: String) {
        // Add marker to the map
        mMap.addMarker(MarkerOptions().position(point).title(title).snippet(snippet))
            ?.showInfoWindow()
        mMap.animateCamera(CameraUpdateFactory.newLatLng(point))
    }

    private fun showDeleteConfirmationDialog(marker: Marker) {
        AlertDialog.Builder(this)
            .setTitle("Delete Pinpoint")
            .setMessage("Are you sure you want to delete this pinpoint?")
            .setPositiveButton("Yes") { dialog, which ->
                // Delete the pinpoint
                deletePinpointFromFirestore(markersMap[marker] ?: "")
                marker.remove()
                markersMap.remove(marker)
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
    private fun deletePinpointFromFirestore(pinpointId: String) {
        // Delete pinpoint from Firestore
        db.collection("pinpoints")
            .document(pinpointId)
            .delete()
            .addOnSuccessListener {
                println("Pinpoint deleted successfully")
            }
            .addOnFailureListener { e ->
                println("Error deleting pinpoint: $e")
            }
    }

    private fun savePinpointToFirestore(point: LatLng, title: String, snippet: String) {
        // Create a new document with a generated ID
        val pinpoint = hashMapOf(
            "latitude" to point.latitude,
            "longitude" to point.longitude,
            "title" to title,
            "snippet" to snippet
        )

        // Add a new document with a generated ID to Firestore
        db.collection("pinpoints")
            .add(pinpoint)
            .addOnSuccessListener { documentReference ->
                println("Pinpoint added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                println("Error adding pinpoint: $e")
            }
    }

    private fun loadPinpointsFromFirestore() {
        // Retrieve pinpoints from Firestore
        db.collection("pinpoints")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val latitude = document.getDouble("latitude")
                    val longitude = document.getDouble("longitude")
                    val title = document.getString("title")
                    val snippet = document.getString("snippet")

                    if (latitude != null && longitude != null && title != null && snippet != null) {
                        // Add marker for each pinpoint on the map
                        val point = LatLng(latitude, longitude)
                        addMarker(point, title, snippet)
                    }
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting pinpoints: $exception")
            }
    }
}
