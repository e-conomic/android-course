package com.e_conomic.weatherapp.domain.mappers

import com.e_conomic.weatherapp.data.Forecast
import com.e_conomic.weatherapp.data.ForecastResult
import com.e_conomic.weatherapp.domain.model.ForecastList
import java.text.DateFormat
import java.util.*
import com.e_conomic.weatherapp.domain.model.Forecast as ModelForecast

class ForecastDataMapper {

    companion object {
        val NO_ICON = "ForecastDataMapper:noIcon"
        val NO_DESCRIPTION = "ForecastDataMapper:noDescription"
    }

    fun convertToForecastModel(forecast: ForecastResult): ForecastList {
        return ForecastList(forecast.city.name, forecast.city.country,
                convertToModelForecastList(forecast.list))
    }

    private fun convertToModelForecastList(forecastList: List<Forecast>): List<ModelForecast> {
        return forecastList.map { forecast -> convertToModelForecast(forecast) }
    }

    private fun convertToModelForecast(forecast: Forecast): ModelForecast {
        val description: String
        val iconUrl: String

        if (forecast.weather.isEmpty()) {
            description = NO_DESCRIPTION
            iconUrl = NO_ICON
        } else {
            description = forecast.weather[0].description
            iconUrl = generateIconUrl(forecast.weather[0].icon)
        }

        return ModelForecast(convertDate(forecast.dt), description,
                forecast.temp.max.toInt(), forecast.temp.min.toInt(), iconUrl)
    }

    private fun generateIconUrl(iconCode: String) = "http://openweathermap.org/img/w/$iconCode.png"

    private fun convertDate(date: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return df.format(date * 1000)
    }
}