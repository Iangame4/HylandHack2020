package c.hylandhack.kintober

import android.content.Intent
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
            ibSpeedRun.setImageResource(
                if (pic)
                {

                    R.mipmap.ic_speedrun

                }
                else R.mipmap.ic_speedrun
            )

            pic = !pic
        }

        //changes the biking image of the image button
        ibSpeedRun.setOnClickListener {
            ibSpeedRun.setImageResource(
                if(pic)
                {

                    R.mipmap.ic_launcher_round
                }
                else R.mipmap.ic_launcher_round
            )
            ibBiking.setImageResource(
                if (pic)
                {

                    R.mipmap.ic_speedrun

                }
                else R.mipmap.ic_speedrun
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
            ibSpeedRun.setImageResource(
                if (pic)
                {

                    R.mipmap.ic_speedrun

                }
                else R.mipmap.ic_speedrun
            )
            pic = !pic;
        }
            btnRandom.setOnClickListener {
            val i = Intent(this, RandomActivity::class.java)
            startActivity(i)
        }
    }
}
