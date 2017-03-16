package com.e_conomic.weatherapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.e_conomic.weatherapp.data.ForecastRequest
import com.e_conomic.weatherapp.domain.commands.RequestforecastCommand
import com.e_conomic.weatherapp.domain.model.Forecast
import com.e_conomic.weatherapp.ui.ForecastListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.async

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val forecastList: RecyclerView = find(R.id.forecast_list)
        forecastList.layoutManager = LinearLayoutManager(this)

        doAsync {
            val result = RequestforecastCommand("94043").execute()
            uiThread {
                forecastList.adapter = ForecastListAdapter(result) {toast(it.date)}
            }
        }
    }
}
