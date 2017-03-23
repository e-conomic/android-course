package com.e_conomic.weatherapp.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.e_conomic.weatherapp.R
import com.e_conomic.weatherapp.domain.model.Forecast
import com.e_conomic.weatherapp.extensions.color
import com.e_conomic.weatherapp.extensions.toDateString
import com.e_conomic.weatherapp.ui.utils.ToolbarManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.find
import java.text.DateFormat

class DetailActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy {find<Toolbar>(R.id.toolbar)}

    companion object {
        val FORECAST_EXTRA_KEY = "forecast"
        val FORECAST_CITY_EXTRA_KEY = "forecastCity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
<<<<<<< HEAD
        title = intent.getStringExtra(FORECAST_CITY_EXTRA_KEY)
=======
        initToolbar()
        toolbarTitle = intent.getStringExtra(FORECAST_CITY_EXTRA_KEY)
>>>>>>> adac4e1... Added simple custom toolbar
        bindForecast(intent.getSerializableExtra(FORECAST_EXTRA_KEY) as Forecast)
    }

    private fun bindForecast(forecast: Forecast): Unit {
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