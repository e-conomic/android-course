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
                                0L, //cityId
                                "Copenhagen", //name
                                Coordinates(0f, 0f),
                                "Denmark", //country
                                1 //population
                        ),
                        listOf(Forecast(
                                123L, //date
                                Temperature(
                                        0f, //day
                                        -5f, //min
                                        10f, //max
                                        0f, //night
                                        0f, //eve
                                        0f //morn
                                ),
                                0f, //pressure
                                0, //humidity
                                listOf(Weather(
                                        0L, //id
                                        "weatherMain",
                                        "weatherDescription",
                                        "weatherIcon"
                                )),
                                0f, //speed
                                0, //degrees
                                0, //clouds
                                0f //rain
                        )))
        )

        val expectedResult = ForecastList(
                "Copenhagen",
                "Denmark",
                listOf(modelForecast(
                        123L, //date
                        "weatherDescription",
                        10, //high
                        -5, //low
                        "http://openweathermap.org/img/w/weatherIcon.png"
                ))
        )

        assertEquals(ForecastDataMapper().convertToForecastModel(request.execute()),
                expectedResult)
    }
}