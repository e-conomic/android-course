package com.e_conomic.weatherapp.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.e_conomic.weatherapp.App
import com.e_conomic.weatherapp.R
import com.e_conomic.weatherapp.domain.mappers.ForecastDataMapper
import com.e_conomic.weatherapp.domain.model.Forecast
import com.e_conomic.weatherapp.domain.model.ForecastList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastListAdapter(val weekForecast: ForecastList, val itemClick: (Forecast) -> Unit) :
        RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(weekForecast[position])
    }

    override fun getItemCount() = weekForecast.size

    class ViewHolder(view: View, val itemClick: (Forecast) -> Unit) :
            RecyclerView.ViewHolder(view) {

        fun bindForecast(forecast: Forecast) {
            with(forecast) {

                if (iconUrl == ForecastDataMapper.NO_ICON) {
                    itemView.icon.setImageResource(R.mipmap.ic_launcher)
                } else {
                    Picasso.with(itemView.context).load(iconUrl).into(itemView.icon)
                }

                itemView.date.text = date
                itemView.description.text = if (description == ForecastDataMapper.NO_DESCRIPTION)
                    App.instance.resources.getString(R.string.default_weather_description)
                    else description
                itemView.maxTemperature.text = high.toString()
                itemView.minTemperature.text = low.toString()
                itemView.setOnClickListener { itemClick(this) }
            }
        }

    }
}