package com.example.weatherapp.presentation.favoriteCityList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.network.City
import com.example.weatherapp.databinding.FavoriteCityItemViewBinding


class FavoriteCitiesAdapter(
    private val onClick: (City) -> Unit
) : RecyclerView.Adapter<CityViewHolder>() {

    var data: List<City> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FavoriteCityItemViewBinding.inflate(layoutInflater, parent, false)
        return CityViewHolder(binding, parent.context, onClick)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }
}

class CityViewHolder(
    private val binding: FavoriteCityItemViewBinding,
    private val context: Context,
    private val onClick: (City) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cityWeather: City) {
        setViewProperties(cityWeather)
        binding.root.setOnClickListener { onClick(cityWeather) }
    }

    private fun setViewProperties(city: City) {
        binding.cityNameText.text = city.name
        binding.countryText.text = city.country.name
        binding.conditionImage.setImageResource(
            context.resources.getIdentifier(
                "ic_${city.weather[0].icon}",
                "drawable", context.packageName
            )
        )
    }
}
