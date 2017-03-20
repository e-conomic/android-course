package com.e_conomic.weatherapp.data

import android.util.Log
import com.google.gson.Gson
import com.e_conomic.weatherapp.data.ForecastResult
import java.net.URL

class ForecastRequest(val zipCode: String) {

    // This object will be shared among all instances of this class.
    companion object {
        private val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private val URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json" +
                "&units=metric&cnt=7"
        private val COMPLETE_URL = "${URL}&APPID=${APP_ID}&id="
    }

    fun execute(): ForecastResult {
        val forecastJsonStr = URL(COMPLETE_URL + zipCode).readText()
        // Parse json to the ForecastResult class.
        return Gson().fromJson(forecastJsonStr, ForecastResult::class.java)
    }
}