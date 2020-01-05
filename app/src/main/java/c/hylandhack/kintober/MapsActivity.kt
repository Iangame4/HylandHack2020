package c.hylandhack.kintober

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
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
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.activity_random.*
import kotlinx.android.synthetic.main.activity_random.btnCancel
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import kotlin.random.Random

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, OnSuccessListener<Location> {

    lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var location: Location? = null
    private var poptions: PolylineOptions? = null
    private var looperByRianJohnson = true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        var cancelCount = 0
        btnCancel.setOnClickListener {

            if (cancelCount == 0) {

                cancelCount++
            } else {
                val i = Intent(this, RandomActivity::class.java)
                startActivity(i)
            }
        }

        // THE GO FAB BUTTON
        extFab.setOnClickListener {
            extFab.hide()
        }

        btnArView.setOnClickListener {
            val i = Intent(this, ARCameraActivity::class.java)
            startActivity(i)
        }
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
        fusedLocationClient.lastLocation.addOnSuccessListener(this)
    }



    fun generateDestination(latitude: Double, longitude: Double):LatLng {
        val aMinInDegs = 0.01666667
        var chosenOption: Int = Random.nextInt(1,10)
        var chosenDirection: Int = Random.nextInt(2,3)
        var neuLat: Double = latitude
        var neuLng: Double = longitude
        val distanceIntent = intent.getIntExtra("distance", 10)
        if (chosenDirection == 2){
            neuLat = latitude - ((chosenOption/10.000000001) * (distanceIntent*aMinInDegs))
            neuLng = (((10-chosenOption)/10.000000001)  * (distanceIntent*aMinInDegs)) + longitude
        } else if (chosenDirection == 3) {
            neuLat = latitude - ((chosenOption/10) * (distanceIntent*aMinInDegs))
            neuLng = longitude - (((10-chosenOption)/10) * (distanceIntent*aMinInDegs))
    }
        mMap.addMarker(MarkerOptions().position(LatLng(neuLat, neuLng)).title("get going shitbird"))
        return LatLng(neuLat, neuLng)
    }


    override fun onSuccess(p0: Location?) {

            if (p0 != null) {
                val temp = LatLng(p0.latitude, p0.longitude)
                var ret = LatLng(1.3,3.7)

                mMap.addMarker(MarkerOptions().position(temp).title("Location"))
                this.location = location
                var zoomIn = 14.0f
                mMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        temp,
                        zoomIn
                    )
                )

                val checkIntent = intent.getIntExtra("thing", 0)
                if (checkIntent == 0){
                    ret = generateDestination(p0.latitude, p0.longitude)
                    val str = "https://maps.googleapis.com/maps/api/directions/json?origin=" + temp.latitude.toString() + "," + temp.longitude.toString() + "&destination=" + ret.latitude.toString() + "," + ret.longitude.toString() + "&mode=walking" + "&key=AIzaSyAeXFMly_AQUddMqZqh6fj2GblPijJCCiQ"
                    val dt = DownloadTask()
                    dt.execute(str)
                } else  {
                        mMap.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
                            override fun onMapClick(point: LatLng) {
                                if (looperByRianJohnson){
                                    looperByRianJohnson = false
                                mMap.addMarker(MarkerOptions().position(point).title("Custom Destination"))
                                ret = point
                                val str =
                                    "https://maps.googleapis.com/maps/api/directions/json?origin=" + temp.latitude.toString() + "," + temp.longitude.toString() + "&destination=" + ret.latitude.toString() + "," + ret.longitude.toString() + "&mode=walking" + "&key=AIzaSyAeXFMly_AQUddMqZqh6fj2GblPijJCCiQ"
                                val dt = DownloadTask()
                                dt.execute(str)
                            }
                        }
                    })
                }
            }
    }

    inner class DownloadTask : AsyncTask<String, String, PolylineOptions?>() {

        fun readData(url: String?): String {
            val connection = URL(url).openConnection()
            connection.connect()
            val istream = connection.getInputStream()
            val br = BufferedReader(InputStreamReader(istream))
            val sb = StringBuffer()
            var line: String?
            do {
                line = br.readLine()
                sb.append(line)
            } while (line != null)
            val data = sb.toString()
            br.close()
            return data
        }

        override fun doInBackground(vararg p0: String?): PolylineOptions? {


            val data = readData(p0[0])
            val json =
                JSONObject(data).getJSONArray("routes").getJSONObject(0).getJSONArray("legs")
                    .getJSONObject(0).getJSONArray("steps")
            var i = 0
            var options = PolylineOptions().width(5f).color(Color.RED)
            var str = "https://roads.googleapis.com/v1/snapToRoads?path="
            while (i < json.length()) {
                val current = json.getJSONObject(i)
                val start = current.getJSONObject("start_location")
                str += start.getDouble("lat").toString() + "," + start.getDouble("lng").toString() + "|"
                val end = current.getJSONObject("end_location")
                str += end.getDouble("lat").toString() + "," + end.getDouble("lng").toString()
                if (i < json.length() - 1) {
                    str += "|"
                }
                i++
            }
            str += "&interpolate=true&key=AIzaSyAeXFMly_AQUddMqZqh6fj2GblPijJCCiQ"
            val data2 = readData(str)
            val json2 = JSONObject(data2).getJSONArray("snappedPoints")
            var j = 0
            while (j < json2.length()) {
                var temp = json2.getJSONObject(j).getJSONObject("location")
                options.add(LatLng(temp.getDouble("latitude"), temp.getDouble("longitude")))
                j++
            }

            return options
        }

        override fun onPostExecute(result: PolylineOptions?) {
            if (result != null) {
                mMap.addPolyline(result)
            }
        }
    }

}
