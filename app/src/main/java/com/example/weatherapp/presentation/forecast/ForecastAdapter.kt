package com.example.weatherapp.presentation.forecast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.network.Forecast
import com.example.weatherapp.databinding.ForecastItemViewBinding
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

class ForecastAdapter() :
    RecyclerView.Adapter<ForecastViewHolder>() {

    var data: List<Forecast> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var timeZone: Int? = null

    var isDayVisible = false

    val firstDayTime = mutableMapOf<Int, Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ForecastItemViewBinding.inflate(layoutInflater, parent, false)
        return ForecastViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = data[position]
        val dateTime = DateTime(item.dt * 1000, DateTimeZone.forOffsetMillis(timeZone!! * 1000))
        if (firstDayTime[dateTime.dayOfMonth().get()] != null) {
            isDayVisible = firstDayTime[dateTime.dayOfMonth().get()] == dateTime.hourOfDay().get()
        } else {
            firstDayTime[dateTime.dayOfMonth().get()] = dateTime.hourOfDay().get()
            isDayVisible = true
        }
        holder.bind(item, isDayVisible, dateTime)
    }

    override fun getItemCount(): Int = data.size
}

class ForecastViewHolder(
    private val binding: ForecastItemViewBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(forecastItem: Forecast, isDayVisible: Boolean, dateTime: DateTime) {
        setViewProperties(forecastItem, isDayVisible, dateTime)
    }

    private fun setViewProperties(
        forecastItem: Forecast,
        isDayVisible: Boolean,
        dateTime: DateTime
    ) {

        if (isDayVisible) {
            binding.day.visibility = View.VISIBLE
        } else {
            binding.day.visibility = View.INVISIBLE
        }
        binding.apply {
            descriptionText.text = forecastItem.weather[0].description
            windSpeed.text = context.getString(
                R.string.wind_speed_forecast_format,
                forecastItem.wind.speed.toInt()
            )
            temperature.text = context.resources.getString(
                R.string.decrees_format,
                forecastItem.main.temperature.toInt()
            )
            time.text = context.getString(
                R.string.hour_and_minute_date_format,
                dateTime.hourOfDay().asText,
                dateTime.minuteOfHour().asText
            )
            day.text = context.getString(
                R.string.day_date_format,
                dateTime.dayOfMonth().asText,
                dateTime.monthOfYear().asText
            )
            humidity.text = context.getString(
                R.string.humidity_forecast_format,
                forecastItem.main.humidity
            )
            conditionImage.setImageResource(
                context.resources.getIdentifier(
                    "ic_${forecastItem.weather[0].icon}",
                    "drawable", context.packageName
                )
            )

            if (forecastItem.rain != null) {
                precipitationImage.setImageResource(R.drawable.ic_rain)
                precipitationText.text = context.getString(
                    R.string.rain_forecast_format,
                    forecastItem.rain.forThreeHours
                )
            } else if (forecastItem.snow != null) {
                precipitationImage.setImageResource(R.drawable.ic_snow)
                precipitationText.text = context.getString(
                    R.string.snow_forecast_format,
                    forecastItem.snow.forThreeHours
                )
            } else {
                precipitationImage.setImageResource(R.drawable.ic_rain)
                precipitationText.text = 0.toString()
            }
        }
    }
}