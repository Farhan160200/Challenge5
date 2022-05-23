package com.farhanfarkaann.challenge5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(BuildConfig.FLAVOR.equals("demo")) {
            Toast.makeText(this, "ini adlaah demo", Toast.LENGTH_SHORT).show()
        }else if(BuildConfig.FLAVOR.equals("full")) {
            Toast.makeText(this, "ini adlaah full", Toast.LENGTH_SHORT).show()


        }
    }
}