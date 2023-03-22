package com.example.adnetworkdemo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.aimz.admob.libs.Ads
import com.aimz.admob.libs.admob.ENUMS

import com.aimz.admob.libs.adslib.TestAds

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Example of library usage

        val button = findViewById<Button>(R.id.button)
        val buttonload = findViewById<Button>(R.id.buttonload)
        //val adFrame = findViewById<FrameLayout>(R.id.native_ad_layout)

        val text = findViewById<TextView>(R.id.title_text_view)
        text.isSelected = true

        /*to show admob ads save true
        to show applovin ads save false*/
        TestAds.getTestAds(this, ENUMS.ADMOB, packageName)

        //LiveAds.getLiveAds(this, packageName)

//        InHouseAds.getInHouseAds(this, "updatesoftware.checker.online.finder.update")

        Ads.loadNative(this, this, null, getString(R.string.ads_lib_app_name), packageName,
               ENUMS.LARGE_ADS, ENUMS.WHITE, false,"#2f5445")



        button.setOnClickListener {
            val intent = Intent(this, NextActivity::class.java)
            Ads.showInter(this, this, intent, false)
        }

        buttonload.setOnClickListener {
            Ads.loadInter(this, this)
        }

        Ads.showActivityBanner(this, this)


        //InHouseNativeAds.showInHouseAds(this, this, getString(R.string.ads_lib_app_name),
          //  packageName,  ENUMS.SMALL_ADS)

       // playVideo()
    }

   /* private fun playVideo() {
        val youTubePlayerView = findViewById<YouTubePlayerView>(R.id.youtube)
        youTubePlayerView.enableAutomaticInitialization = false
        val iFramePlayerOptions: IFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(0)
            .build()
        youTubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "g5qauZ8Vn2w"
                youTubePlayer.loadVideo(videoId, 0f)
            }
        }, iFramePlayerOptions)
    }*/

}