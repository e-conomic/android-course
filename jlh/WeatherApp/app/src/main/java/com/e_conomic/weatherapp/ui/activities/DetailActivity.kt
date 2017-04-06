package com.e_conomic.weatherapp.ui.activities

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.TextView
import com.e_conomic.weatherapp.R
import com.e_conomic.weatherapp.domain.model.Forecast
import com.e_conomic.weatherapp.extensions.color
import com.e_conomic.weatherapp.extensions.toDateString
import com.e_conomic.weatherapp.ui.utils.ToolbarAppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.find
import java.text.DateFormat

class DetailActivity : ToolbarAppCompatActivity() {

    override val toolbar by lazy {find<Toolbar>(R.id.toolbar)}
    override val layoutId = R.layout.activity_detail

    val DETAIL_ACTIVITY_TAG = "DetailActivity"

    companion object {
        val FORECAST_EXTRA_KEY = "forecast"
        val FORECAST_CITY_EXTRA_KEY = "forecastCity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbarTitle = intent.getStringExtra(FORECAST_CITY_EXTRA_KEY)
        enableHomeAsUp { onBackPressed() }

        val forecast = intent.getSerializableExtra(FORECAST_EXTRA_KEY) as? Forecast

        if (forecast == null) {
            Log.e(DETAIL_ACTIVITY_TAG, "Forecast is null when trying to bind to UI")
            weatherDescription.text = resources.getString(R.string.forecast_not_found)
        } else {
            bindForecast(forecast)
        }
    }

    private fun bindForecast(forecast: Forecast) {
        with (forecast) {
            Picasso.with(applicationContext).load(iconUrl).into(icon)
            supportActionBar?.subtitle = date.toDateString(DateFormat.FULL)
            weatherDescription.text = description
            bindWeather(high to maxTemperature, low to minTemperature)
        }

    }

    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        (temperature, view) ->
            view.text = temperature.toString()
            view.setTextColor(color(
                    when (temperature) {
                        in -50..0 -> android.R.color.holo_blue_dark
                        in 0..10 -> android.R.color.holo_blue_bright
                        in 10..25 -> android.R.color.holo_orange_light
                        else -> android.R.color.holo_orange_dark
                    }))
    }

}