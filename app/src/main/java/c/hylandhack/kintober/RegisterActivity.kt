package c.hylandhack.kintober

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.etPassword
import kotlinx.android.synthetic.main.activity_main.etUsername
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegSubmit.setOnClickListener {

            fun isEmailValid(email: String): Boolean {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }

            val userName = etUsername.text;
            val password = etPassword.text;

            if (userName.length <= 0 || password.length <= 0) {
                Toast.makeText(this@RegisterActivity, "PARAMETER MISSING", Toast.LENGTH_LONG).show()

            } else if (isEmailValid(userName.toString()) == true) {
                Toast.makeText(this@RegisterActivity, "SUCCESS", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this@RegisterActivity, "FAILURE", Toast.LENGTH_LONG).show()

            }
        }


    }
}
