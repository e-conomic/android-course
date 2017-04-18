package com.e_conomic.weatherapp

import com.e_conomic.weatherapp.data.*
import com.e_conomic.weatherapp.domain.mappers.ForecastDataMapper
import com.e_conomic.weatherapp.domain.model.ForecastList
import org.junit.Assert.assertEquals
import com.e_conomic.weatherapp.domain.model.Forecast as modelForecast
import org.junit.Test

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class ForecastDataMapperTest {

    @Test fun testCorrectMappingToModel() {
        val request = mock(ForecastRequest::class.java)
        `when`(request.execute()).thenReturn(
                ForecastResult(
                        City(
                                id = 0L,
                                name = "Copenhagen",
                                coord = Coordinates(0f, 0f),
                                country = "Denmark",
                                population = 1
                        ),
                        listOf(Forecast(
                                dt = 123L,
                                temp = Temperature(
                                        day = 0f,
                                        min = -5f,
                                        max = 10f,
                                        night = 0f,
                                        eve = 0f,
                                        morn = 0f
                                ),
                                pressure = 0f,
                                humidity = 0,
                                weather = listOf(Weather(
                                        id = 0L, //id
                                        main = "weatherMain",
                                        description = "weatherDescription",
                                        icon = "weatherIcon"
                                )),
                                speed = 0f,
                                deg = 0,
                                clouds = 0,
                                rain = 0f
                        )))
        )

        val expectedResult = ForecastList(
                city = "Copenhagen",
                country = "Denmark",
                dailyForecast = listOf(modelForecast(
                        date = 123L,
                        description = "weatherDescription",
                        high = 10,
                        low = -5,
                        iconUrl = "http://openweathermap.org/img/w/weatherIcon.png"
                ))
        )

        assertEquals(ForecastDataMapper().convertToForecastModel(request.execute()),
                expectedResult)
    }
}