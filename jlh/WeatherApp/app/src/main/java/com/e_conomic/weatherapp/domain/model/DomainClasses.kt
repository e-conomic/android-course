package com.e_conomic.weatherapp.domain.model

data class ForecastList(val city: String,
                        val country: String,
                        val dailyForecast: List<Forecast>) {
    operator fun get(position: Int) = dailyForecast[position]
    val size: Int
        get() = dailyForecast.size
}

data class Forecast(val date: String,
                    val description: String,
                    val high: Int,
                    val low: Int,
                    val iconUrl: String)
