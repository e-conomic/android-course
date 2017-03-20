package com.e_conomic.weatherapp.ui.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.e_conomic.weatherapp.R

class DetailActivity : AppCompatActivity() {

    companion object {
        val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_detail)
        title = intent.getStringExtra(CITY_NAME)
    }

}