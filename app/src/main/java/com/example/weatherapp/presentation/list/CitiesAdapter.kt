package com.example.weatherapp.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.database.CityWeather
import com.example.weatherapp.databinding.CityItemViewBinding

class CitiesAdapter(private val onClick: (CityWeather) -> Unit) : RecyclerView.Adapter<CityViewHolder>() {

    var data: List<CityWeather> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CityItemViewBinding.inflate(layoutInflater, parent, false)
        return CityViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

}

class CityViewHolder(private val binding: CityItemViewBinding, private val onClick: (CityWeather) -> Unit): RecyclerView.ViewHolder(binding.root) {

    fun bind(cityWeather: CityWeather) {
        setViewProperties(cityWeather)
        binding.root.setOnClickListener { onClick(cityWeather) }
    }

    private fun setViewProperties(cityWeather: CityWeather){
        binding.cityNameText.text = cityWeather.name
        binding.countryText.text = cityWeather.country
        when (cityWeather.condition) {
            "snowy" -> binding.conditionImage.setImageResource(R.mipmap.ic_snow_icon_foreground)
            "rainy" -> binding.conditionImage.setImageResource(R.mipmap.ic_rain_icon_foreground)
            "cloudy" -> binding.conditionImage.setImageResource(R.mipmap.ic_cloud_icon_foreground)
            else -> binding.conditionImage.setImageResource(R.mipmap.ic_sun_icon_foreground)
        }
    }
}
