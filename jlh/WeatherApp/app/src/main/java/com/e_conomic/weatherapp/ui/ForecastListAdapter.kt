package com.e_conomic.weatherapp.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.e_conomic.weatherapp.R
import com.e_conomic.weatherapp.domain.model.Forecast
import com.e_conomic.weatherapp.domain.model.ForecastList
import com.squareup.picasso.Picasso
import ctx
import org.jetbrains.anko.find

class ForecastListAdapter(val weekForecast: ForecastList, val itemClick: ForecastListAdapter.OnItemClickListener) :
        // Custom ViewHolder implementation?
        RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        operator fun invoke(forecast: Forecast)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindforecast(weekForecast[position])
    }

    override fun getItemCount(): Int = weekForecast.size()

    class ViewHolder(view: View, val itemClick: OnItemClickListener) : RecyclerView.ViewHolder(view) {

        private val iconView: ImageView
        private val dateView: TextView
        private val descriptionView: TextView
        private val maxTemperatureView: TextView
        private val minTemperatureView: TextView

        init {
            iconView = view.find(R.id.icon)
            dateView = view.find(R.id.date)
            descriptionView = view.find(R.id.description)
            maxTemperatureView = view.find(R.id.maxTemperature)
            minTemperatureView = view.find(R.id.minTemperature)
        }

        fun bindforecast(forecast: Forecast) {
            with(forecast) {
                Picasso.with(itemView.ctx).load(iconUrl).into(iconView)
                dateView.text = date
                descriptionView.text = description
                maxTemperatureView.text = high.toString()
                minTemperatureView.text = low.toString()
                // TODO Why itemView? (Field in RecyclerView.ViewHolder)
                itemView.setOnClickListener { itemClick(this) }
            }
        }

    }
}