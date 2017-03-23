package com.e_conomic.weatherapp.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.e_conomic.weatherapp.R
import com.e_conomic.weatherapp.domain.commands.RequestForecastCommand
import com.e_conomic.weatherapp.ui.ForecastListAdapter
import com.e_conomic.weatherapp.ui.utils.ToolbarManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy {find<Toolbar>(R.id.toolbar)}

    val COPENHAGEN_ID = "2618425"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()

        forecastList.layoutManager = LinearLayoutManager(this)


        doAsync {
            val forecastListResult = RequestForecastCommand(COPENHAGEN_ID).execute()
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
}
