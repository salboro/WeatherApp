package com.example.weatherapp.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.City
import com.example.weatherapp.databinding.CityItemViewBinding

class CitiesAdapter(val clickListener: CityClickListener) : RecyclerView.Adapter<CityViewHolder>() {

    var data: List<City> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CityItemViewBinding.inflate(layoutInflater, parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, clickListener)
    }

}

class CityViewHolder(private val binding: CityItemViewBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(city: City, clickListener: CityClickListener) {
        binding.cityNameText.text = city.name
        binding.countryText.text = city.country
        binding.city = city
        when (city.weather.condition) {
            "snowy" -> binding.conditionImage.setImageResource(R.mipmap.ic_snow_icon_foreground)
            "rainy" -> binding.conditionImage.setImageResource(R.mipmap.ic_rain_icon_foreground)
            "cloudy" -> binding.conditionImage.setImageResource(R.mipmap.ic_cloud_icon_foreground)
            else -> binding.conditionImage.setImageResource(R.mipmap.ic_sun_icon_foreground)
        }
        binding.clickListener = clickListener
    }
}

class CityClickListener(val clickListener: (city: City) -> Unit) {
    fun onClick(city: City) = clickListener(city)
}