package com.example.yandexnav.presentation

import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yandexnav.R
import com.example.yandexnav.data.LocationRepository
import com.example.yandexnav.databinding.FragmentMapBinding
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

private const val API_KEY = "a9489bda-d071-4d55-9a12-067fae42f626"

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding

    private lateinit var mapView: MapView

    private lateinit var searchManager: SearchManager
    private lateinit var searchSession: Session
    private var streetInfo = ""
    private var longitudeInfo = ""
    private var latitudeInfo = ""

//    private val placemarkTapListener = MapObjectTapListener { _, point ->
//
//        findNavController().navigate(R.id.action_mapFragment_to_sightFragment)
//        true
//    }

    private val searchListener = object : Session.SearchListener {
        override fun onSearchResponse(response: Response) {
//            val street = response.collection.children.firstOrNull()?.obj
//                ?.metadataContainer
//                ?.getItem(ToponymObjectMetadata::class.java)
//                ?.address
//                ?.components
//                ?.firstOrNull() { it.kinds.contains(Address.Component.Kind.STREET) }
//                ?.name ?: "Информация по улице не найдена"
//
//            val houseNumber = response.collection.children.firstOrNull()?.obj
//                ?.metadataContainer
//                ?.getItem(ToponymObjectMetadata::class.java)
//                ?.address
//                ?.components
//                ?.firstOrNull { it.kinds.contains(Address.Component.Kind.HOUSE) }
//                ?.name ?: "Номер дома не найден"

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


//            streetInfo = name.toString()
//            longitudeInfo = longitude.toString()
//            latitudeInfo = latitude.toString()
//            Toast.makeText(requireContext(), street, Toast.LENGTH_SHORT).show()
            dialog(name,longitude,latitude)
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

//        bolshoi.addTapListener(placemarkTapListener)


//        zum.addTapListener(placemarkTapListener)
//        redSquare.addTapListener(placemarkTapListener)

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



        return binding.root

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