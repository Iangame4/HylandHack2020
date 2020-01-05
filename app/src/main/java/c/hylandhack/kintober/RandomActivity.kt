package c.hylandhack.kintober

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.activity_random.*


class RandomActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random)
        val transportIntent = intent.getIntExtra("transport method", 1)
        val etMiles = findViewById<EditText>(R.id.etMiles)
        val sbMiles = findViewById<SeekBar>(R.id.sbMiles)

        // Set a SeekBar change listener
        sbMiles.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                etMiles.setText("$i")

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something

            }
        })

        btnCancel.setOnClickListener {
            val i = Intent(this, MainMenuActivity::class.java)
            startActivity(i)
        }
        btnConfirm.setOnClickListener{
            val i = Intent(this, MapsActivity::class.java)
            i.putExtra("distance", sbMiles.progress)
            i.putExtra("transport method", transportIntent)
            startActivity(i)
        }
    }
}
