package com.e_conomic.weatherapp.ui.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.e_conomic.weatherapp.R
import com.e_conomic.weatherapp.domain.commands.RequestForecastCommand
import com.e_conomic.weatherapp.extensions.Preference
import com.e_conomic.weatherapp.ui.ForecastListAdapter
import com.e_conomic.weatherapp.ui.utils.ToolbarAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : ToolbarAppCompatActivity() {

    override val toolbar by lazy {find<Toolbar>(R.id.toolbar)}
    override val layoutId = R.layout.activity_main

    val cityId: Long by Preference(this, SettingsActivity.CITY_ID, SettingsActivity.DEFAULT_ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }

    private fun loadForecast() =
            doAsync {
                val forecastListResult = RequestForecastCommand(cityId).execute()
                uiThread {
                    forecastList.adapter = ForecastListAdapter(forecastListResult) { forecast ->
                        startActivity<DetailActivity>(
                                DetailActivity.FORECAST_EXTRA_KEY to forecast,
                                DetailActivity.FORECAST_CITY_EXTRA_KEY to forecastListResult.city)
                    }
                    toolbarTitle = "${forecastListResult.city} (${forecastListResult.country})"
                }
            }
}