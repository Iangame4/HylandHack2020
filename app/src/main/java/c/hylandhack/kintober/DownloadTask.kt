package c.hylandhack.kintober

import android.graphics.Color
import android.os.AsyncTask
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class DownloadTask : AsyncTask<String, String, PolylineOptions>(){
    override fun doInBackground(vararg p0: String?): PolylineOptions {
        val url =  URL("https://maps.googleapis.com/maps/api/directions/json?origin=Disneyland&destination=Universal+Studios+Hollywood&key=AIzaSyAeXFMly_AQUddMqZqh6fj2GblPijJCCiQ")
        val connection = url.openConnection()
        connection.connect()
        val istream = connection.getInputStream()
        val br = BufferedReader(InputStreamReader(istream))
        val sb = StringBuffer()
        var line: String? = null
        do {
            line = br.readLine()
            sb.append(line)
        } while(line != null)
        val data = sb.toString()
        br.close()
        val json = JSONObject(data).getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps")
        var i = 0
        var options = PolylineOptions().width(5f).color(Color.RED)
        while(i < json.length()) {
            val current = json.getJSONObject(i)
            val start = current.getJSONObject("end_location")
            options.add(LatLng(start.getDouble("lat"), start.getDouble("lng")))
            val end = current.getJSONObject("start_location")
            options.add(LatLng(end.getDouble("lat"), end.getDouble("lng")))
            i++
        }
        return options
    }

}