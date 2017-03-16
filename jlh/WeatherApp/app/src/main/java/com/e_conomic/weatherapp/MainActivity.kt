package com.e_conomic.weatherapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.e_conomic.weatherapp.domain.commands.RequestforecastCommand
import com.e_conomic.weatherapp.ui.ForecastListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forecastList.layoutManager = LinearLayoutManager(this)

        doAsync {
            val result = RequestforecastCommand("94043").execute()
            uiThread {
                forecastList.adapter = ForecastListAdapter(result) {toast(it.date)}
            }
        }
    }
}
