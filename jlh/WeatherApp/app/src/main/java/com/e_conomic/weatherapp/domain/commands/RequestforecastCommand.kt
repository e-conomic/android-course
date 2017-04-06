package com.e_conomic.weatherapp.domain.commands

import com.e_conomic.weatherapp.data.ForecastRequest
import com.e_conomic.weatherapp.domain.mappers.ForecastDataMapper
import com.e_conomic.weatherapp.domain.model.ForecastList

class RequestForecastCommand(val cityId: Long): Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(cityId)
        return ForecastDataMapper().convertToForecastModel(forecastRequest.execute())
    }
}