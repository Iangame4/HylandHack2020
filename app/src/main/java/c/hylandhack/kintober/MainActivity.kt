package c.hylandhack.kintober

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Login Form
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnReset.setOnClickListener {
            // clearing userName and password edit text views on reset button click
            etUserName.setText("")
            etPassword.setText("")
        }

        // set on-click listener
        btnSubmit.setOnClickListener {
            val userName = etUserName.text;
            val password = etPassword.text;
            Toast.makeText(this@MainActivity, userName, Toast.LENGTH_LONG).show()

            // your code to validate the userName and password combination
            // and verify the same

            val i = Intent(this, MapsActivity::class.java)
            startActivity(i)



        }

        test1.setOnClickListener {
            val i = Intent(this, MainMenuActivity::class.java)
            startActivity(i)

        }
    }
}