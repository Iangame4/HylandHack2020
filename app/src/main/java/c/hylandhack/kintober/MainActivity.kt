package c.hylandhack.kintober

import android.content.Intent
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity

/**
 * Login Form
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnReset.setOnClickListener {
            // clearing userName and password edit text views on reset button click
            etUsername.setText("")
            etPassword.setText("")
        }

        // set on-click listener
        btnSubmit.setOnClickListener {
            val userName = etUsername.text;
            val password = etPassword.text;
            Toast.makeText(this@MainActivity, userName, Toast.LENGTH_LONG).show()

            // your code to validate the userName and password combination
            // and verify the same

        }

        btnRegister.setOnClickListener {
            val i = Intent(this, MainMenuActivity::class.java)
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
    }
}