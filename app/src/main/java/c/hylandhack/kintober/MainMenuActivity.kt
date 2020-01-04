package c.hylandhack.kintober

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)


        var pic = true;
        ibBiking.setOnClickListener {
            ibBiking.setImageResource(
                if(pic) R.mipmap.ic_launcher_round else R.mipmap.ic_launcher
            )
            pic = !pic
        }

        var pic2 = false;
        ibWalking.setOnClickListener {
            ibWalking.setImageResource(
                if (pic2) R.mipmap.ic_walking_round else R.mipmap.ic_walking
            )
            pic2 = !pic2;
        }
    }
}
