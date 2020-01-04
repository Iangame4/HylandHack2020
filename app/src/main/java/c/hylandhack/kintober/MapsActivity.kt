package c.hylandhack.kintober

import android.Manifest
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import kotlin.random.Random

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var location: Location? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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
        val temp = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this, temp, 1)
        mMap = googleMap
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? -> location?.let {
                mMap.addMarker(MarkerOptions().position(LatLng(it.latitude, it.longitude)).title("Location"))
                this.location = location
                var zoomIn: Float = 14.0f
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), zoomIn))
                generateDestination(location?.latitude, location?.longitude)

        }}
    }

    fun generateDestination(latitude: Double?, longitude: Double?) {
        latitude?.let{
            longitude?.let{
                val aMinInDegs = 0.01666667
                val ratioJerks = mapOf(0 to 0.0,1 to 0.1, 2 to 0.2, 3 to 0.3, 4 to 0.4, 5 to 0.5, 6 to 0.6, 7 to 0.7, 8 to 0.8, 9 to 0.9, 10 to 1)
                var chosenOption: Int = Random.nextInt(1,10)
                var chosenDirection: Int = Random.nextInt(1,4)
                var neuLat: Double = latitude
                var neuLng: Double = longitude
                if (chosenDirection == 1){
                    neuLat = (ratioJerks[chosenOption] as Double * (10.0*aMinInDegs)) + latitude
                    neuLng = (ratioJerks[10-chosenOption] as Double *(10.0*aMinInDegs)) + longitude
                } else if (chosenDirection == 2){
                    neuLat = latitude - (ratioJerks[chosenOption] as Double * (10.0*aMinInDegs))
                    neuLng = (ratioJerks[10-chosenOption] as Double *(10.0*aMinInDegs)) + longitude
                } else if (chosenDirection == 3) {
                    neuLat = latitude - (ratioJerks[chosenOption] as Double * (10.0*aMinInDegs))
                    neuLng = longitude - (ratioJerks[10-chosenOption] as Double *(10.0*aMinInDegs))
                } else if (chosenDirection == 4) {
                    neuLat = latitude + (ratioJerks[chosenOption] as Double * (10.0 * aMinInDegs))
                    neuLng =
                        longitude - (ratioJerks[10 - chosenOption] as Double * (10.0 * aMinInDegs))
                }
                mMap.addMarker(MarkerOptions().position(LatLng(neuLat, neuLng)).title("get going shitbird"))
            }
        }

    }
}
