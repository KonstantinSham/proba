package com.example.yandexnav.presentation

import android.Manifest
import android.app.Dialog
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yandexnav.R
import com.example.yandexnav.data.App
import com.example.yandexnav.data.LocationRepository
import com.example.yandexnav.databinding.FragmentMapBinding
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessaging
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapEvent
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.Address
import com.yandex.mapkit.search.BusinessObjectMetadata
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManager
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.search.ToponymObjectMetadata
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider
import kotlin.random.Random

private const val API_KEY = "a9489bda-d071-4d55-9a12-067fae42f626"

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding

    private lateinit var mapView: MapView

    private lateinit var searchManager: SearchManager
    private lateinit var searchSession: Session

    private val searchListener = object : Session.SearchListener {
        override fun onSearchResponse(response: Response) {
            val name = response.collection.children.firstOrNull()?.obj
                ?.name


            val longitude = response.collection.children.firstOrNull()?.obj
                ?.metadataContainer
                ?.getItem(ToponymObjectMetadata::class.java)
                ?.balloonPoint
                ?.longitude

            val latitude = response.collection.children.firstOrNull()?.obj
                ?.metadataContainer
                ?.getItem(ToponymObjectMetadata::class.java)
                ?.balloonPoint
                ?.latitude

            dialog(name, longitude, latitude)
        }

        override fun onSearchError(p0: Error) {
        }
    }


    private val inputListener = object : InputListener {
        override fun onMapTap(map: Map, point: Point) {
            searchSession = searchManager.submit(point, 20, SearchOptions(), searchListener)
        }

        override fun onMapLongTap(map: Map, point: Point) {
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.all { it }) {
                startLocation()
            } else {
                Toast.makeText(requireContext(), "permission is not granted", Toast.LENGTH_SHORT)
                    .show()

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val lacationRepository = LocationRepository()


        MapKitFactory.setApiKey(API_KEY)
        MapKitFactory.initialize(requireContext())



        binding = FragmentMapBinding.inflate(inflater, container, false)



        mapView = binding.mapView

        val imageProvider = ImageProvider.fromResource(
            requireContext(),
            com.yandex.maps.mobile.R.drawable.search_layer_pin_selected_default
        )

        val myLocation =
            mapView.map.mapObjects.addPlacemark(lacationRepository.myLocation, imageProvider)
        val bolshoi = mapView.map.mapObjects.addPlacemark(
            lacationRepository.bolshoiTheatreLocation,
            imageProvider
        )
        val zum = mapView.map.mapObjects.addPlacemark(lacationRepository.zumLocation, imageProvider)
        val redSquare =
            mapView.map.mapObjects.addPlacemark(lacationRepository.redSquareLocation, imageProvider)


        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.ONLINE)

        binding.mapView.map.addInputListener(inputListener)

        binding.zoomInBtn.setOnClickListener {
            changeZoom(true)
        }
        binding.zoomOutBtn.setOnClickListener {
            changeZoom(false)
        }

        binding.currentMark.setOnClickListener {
            mapView.map.move(
                CameraPosition(lacationRepository.bolshoiTheatreLocation, 16.0f, 0f, 0f),
                Animation(Animation.Type.SMOOTH, 1f), null
            )
        }
        binding.errorBtn.setOnClickListener {
            FirebaseCrashlytics.getInstance().log("This is log message")

            createNotification()
//            throw Exception("My first exception")

            try {
                throw Exception("My first exception")
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
            }
//
//            FirebaseMessaging.getInstance().token.addOnCompleteListener {
//                Log.d("registration token", it.result)
//            }
        }



        return binding.root

    }

    private fun createNotification() {

        val intent = Intent(requireContext(), MainActivity::class.java)

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
        else
            PendingIntent.getActivity(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val notification = NotificationCompat.Builder(requireContext(), App.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Hello User.")
            .setContentText("Wish you good luck!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
//        NotificationManagerCompat.from(requireContext()).notify(NOTIFICATION_ID, notification)
        NotificationManagerCompat.from(requireContext()).notify(Random.nextInt(), notification)
    }

    private fun dialog(title: String?, latitude: Double?, longitude: Double?) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_info)
        val titleText = dialog.findViewById<TextView>(R.id.title_textview)
        val latText = dialog.findViewById<TextView>(R.id.description_textview)
        val longText = dialog.findViewById<TextView>(R.id.coordinates_textview)
        titleText.text = title.toString()
        latText.text = latitude.toString()
        longText.text = longitude.toString()
        dialog.setTitle(title)
        dialog.show()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        mapView.onStart()
        checkPermissions()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    private fun startLocation() {
        mapView.map.move(
            CameraPosition(Point(55.76007, 37.61910), 11.0f, 0f, 0f),
            Animation(Animation.Type.SMOOTH, 0f), null
        )
    }

    private fun changeZoom(isZoomIn: Boolean) {
        val currentZoom = mapView.map.cameraPosition.zoom
        val newZoom = if (isZoomIn) currentZoom + 1.0f else currentZoom - 1.0f

        mapView.map.move(
            CameraPosition(
                mapView.map.cameraPosition.target,
                newZoom,
                mapView.map.cameraPosition.azimuth,
                mapView.map.cameraPosition.tilt
            ),
            Animation(Animation.Type.SMOOTH, 1f),
            null
        )
    }

    fun addMapMarker(point: Point) {
        val imageProvider = com.yandex.runtime.image.ImageProvider.fromResource(
            requireContext(),
            com.yandex.maps.mobile.R.drawable.search_layer_pin_selected_default
        )
        val placeMark = mapView.map.mapObjects.addPlacemark(point, imageProvider)
    }

    private fun checkPermissions() {
        if (REQUIRED_PERMISSIONS.all { permission ->
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            startLocation()
        } else {
            launcher.launch(REQUIRED_PERMISSIONS)
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS: Array<String> = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}