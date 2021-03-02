package com.example.weatherapp.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.City
import com.example.weatherapp.databinding.CityItemViewBinding

class CitiesAdapter(private val onClick: (City) -> Unit) : RecyclerView.Adapter<CityViewHolder>() {

    var data: List<City> = emptyList()
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

class CityViewHolder(private val binding: CityItemViewBinding, private val onClick: (City) -> Unit): RecyclerView.ViewHolder(binding.root) {

    fun bind(city: City) {
        setViewProperties(city)
        binding.root.setOnClickListener { onClick(city) }
    }

    private fun setViewProperties(city: City){
        binding.cityNameText.text = city.name
        binding.countryText.text = city.country
        when (city.weather.condition) {
            "snowy" -> binding.conditionImage.setImageResource(R.mipmap.ic_snow_icon_foreground)
            "rainy" -> binding.conditionImage.setImageResource(R.mipmap.ic_rain_icon_foreground)
            "cloudy" -> binding.conditionImage.setImageResource(R.mipmap.ic_cloud_icon_foreground)
            else -> binding.conditionImage.setImageResource(R.mipmap.ic_sun_icon_foreground)
        }
    }
}
