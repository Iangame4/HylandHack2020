package c.hylandhack.kintober

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {

    var pic: Boolean = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)


        //changes the biking image of the image button
        ibBiking.setOnClickListener {
            ibBiking.setImageResource(
                if(pic)
                {

                    R.mipmap.ic_launcher_round
                }
                else R.mipmap.ic_launcher_round
            )
            ibWalking.setImageResource(
                if (pic)
                {

                    R.mipmap.ic_walking

                }
                else R.mipmap.ic_walking
            )
            pic = !pic
        }

        //changes the walking image of the image button

        ibWalking.setOnClickListener {
            ibWalking.setImageResource(
                if (pic)
                {

                    R.mipmap.ic_launcher_round

                }
                else R.mipmap.ic_launcher_round
            )
            ibBiking.setImageResource(
                if(pic)
                {
                    R.mipmap.ic_walking
                }
                else R.mipmap.ic_walking
            )
            pic = !pic;
        }

    }
}
