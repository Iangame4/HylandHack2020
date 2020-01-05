package c.hylandhack.kintober

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {

    var pic: Boolean = true
    var transport: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)


        //changes the biking image of the image button
        ibBiking.setOnClickListener {
            ibBiking.setImageResource(
                if(pic)
                {
                    R.mipmap.ic_biking
                }
                else R.mipmap.ic_biking
            )
            ibWalking.setImageResource(
                if (pic)
                {
                    R.mipmap.ic_walking_man_foreground
                }
                else R.mipmap.ic_walking_man_foreground
            )
            ibSpeedRun.setImageResource(
                if (pic)
                {
                    R.mipmap.ic_speedrun_foreground
                }
                else R.mipmap.ic_speedrun_foreground
            )
            transport = 3
            pic = !pic
        }
        //changes the biking image of the image button
        ibSpeedRun.setOnClickListener {
            ibSpeedRun.setImageResource(
                if(pic)
                {

                    R.mipmap.ic_speedrun
                }
                else R.mipmap.ic_speedrun
            )
            ibBiking.setImageResource(
                if (pic)
                {

                    R.mipmap.ic_biking_foreground

                }
                else R.mipmap.ic_biking_foreground
            )
            ibWalking.setImageResource(
                if (pic)
                {

                    R.mipmap.ic_walking_man_foreground

                }
                else R.mipmap.ic_walking_man_foreground
            )
            transport = 2
            pic = !pic
        }


        //changes the walking image of the image button
        ibWalking.setOnClickListener {
            ibWalking.setImageResource(
                if (pic)
                {

                    R.mipmap.ic_walking_man

                }
                else R.mipmap.ic_walking_man
            )
            ibBiking.setImageResource(
                if(pic)
                {
                    R.mipmap.ic_biking_foreground
                }
                else R.mipmap.ic_biking_foreground
            )
            ibSpeedRun.setImageResource(
                if (pic)
                {

                    R.mipmap.ic_speedrun_foreground

                }
                else R.mipmap.ic_speedrun_foreground
            )
            transport = 1
            pic = !pic;
        }
        btnRandom.setOnClickListener {
            val i = Intent(this, RandomActivity::class.java)
            i.putExtra("transport method", transport)
            startActivity(i)
        }
        btnSelect.setOnClickListener{
            val i = Intent(this, MapsActivity::class.java)
            i.putExtra("thing",1)
            i.putExtra("transport method", transport)
            startActivity(i)
        }
        btnLeaderboard.setOnClickListener{
            val i = Intent(this, LeaderboardActivity::class.java)
            startActivity(i)
        }
        btnOfficial.setOnClickListener{
            val i = Intent(this, MapsActivity::class.java)
            i.putExtra("transport method", transport)
            startActivity(i)
        }
    }
}
