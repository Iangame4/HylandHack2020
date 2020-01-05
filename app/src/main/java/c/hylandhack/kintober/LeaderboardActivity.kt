package c.hylandhack.kintober

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_leaderboard.*

class LeaderboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)
        btnReturn.setOnClickListener{
            val i = Intent(this, MainMenuActivity::class.java)
            startActivity(i)
        }
    }

}
