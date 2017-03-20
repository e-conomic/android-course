package com.e_conomic.weatherapp.domain.commands

import com.e_conomic.weatherapp.data.ForecastRequest
import com.e_conomic.weatherapp.domain.mappers.ForecastDataMapper
import com.e_conomic.weatherapp.domain.model.ForecastList

class RequestForecastCommand(val zipCode: String): Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }
}