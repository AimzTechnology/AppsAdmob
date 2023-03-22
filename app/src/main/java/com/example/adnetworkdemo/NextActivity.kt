package com.example.adnetworkdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aimz.admob.libs.Ads

class NextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)
//        Ads.loadInter(this, this)
    }

    override fun onBackPressed() {
        Ads.adOnBack(this, this)
    }
}