package c.hylandhack.kintober

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

/**
 * Login Form
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val temp = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        ActivityCompat.requestPermissions(this, temp, 1)





        btnReset.setOnClickListener {
            // clearing userName and password edit text views on reset button click
            etUsername.setText("")
            etPassword.setText("")
        }

        // set on-click listener
        btnSubmit.setOnClickListener {


            fun isEmailValid(email: String): Boolean {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }

            val userName = etUsername.text;
            val password = etPassword.text;

            if (userName.length <= 0 || password.length <= 0) {
                Toast.makeText(this@MainActivity, "PARAMETER MISSING", Toast.LENGTH_LONG).show()

            } else if(isEmailValid(userName.toString()) == true){
                Toast.makeText(this@MainActivity, "SUCCESS", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this@MainActivity, "FAILURE", Toast.LENGTH_LONG).show()

            }

            // your code to validate the userName and password combination
            // and verify the same

        }

        btnRegister.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }

        test1.setOnClickListener {
            val i = Intent(this, MainMenuActivity::class.java)
            startActivity(i)

        }

        test2.setOnClickListener {
            val i = Intent(this, MapsActivity::class.java)
            startActivity(i)

        }

        test3.setOnClickListener {
            val i = Intent(this, ARCameraActivity::class.java)
            startActivity(i)

        }
    }
}