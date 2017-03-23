package com.e_conomic.weatherapp.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.e_conomic.weatherapp.App
import com.e_conomic.weatherapp.R
import com.e_conomic.weatherapp.domain.commands.RequestForecastCommand
import com.e_conomic.weatherapp.ui.ForecastListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    val COPENHAGEN_ID = "2618425"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forecastList.layoutManager = LinearLayoutManager(this)

        doAsync {
            val forecastListResult = RequestForecastCommand(COPENHAGEN_ID).execute()
            uiThread {
                title = "Weather in ${forecastListResult.city}"
                forecastList.adapter = ForecastListAdapter(forecastListResult) { forecast ->
                    startActivity<DetailActivity>(
                            DetailActivity.FORECAST_EXTRA_KEY to forecast,
                            DetailActivity.FORECAST_CITY_EXTRA_KEY to forecastListResult.city)
                }
            }
        }
    }
}
