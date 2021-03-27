package com.example.weatherapp.presentation.nearCityList

import android.content.Context
import android.location.Location
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.network.City
import com.example.weatherapp.databinding.CityItemViewBinding
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sqrt

class NearCitiesAdapter(private val onClick: (City) -> Unit) :
    RecyclerView.Adapter<CityViewHolder>() {

    var data: List<City> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var location: Location? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CityItemViewBinding.inflate(layoutInflater, parent, false)
        return CityViewHolder(binding, parent.context, onClick)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, location)
    }
}

class CityViewHolder(
    private val binding: CityItemViewBinding,
    private val context: Context,
    private val onClick: (City) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cityWeather: City, location: Location?) {
        if (location != null) {
            setViewProperties(cityWeather, location)
            binding.root.setOnClickListener { onClick(cityWeather) }
        }
    }

    private fun setViewProperties(city: City, location: Location) {
        binding.cityNameText.text = city.name
        binding.countryText.text = city.country.name
        binding.conditionImage.setImageResource(
            context.resources.getIdentifier(
                "ic_${city.weather[0].icon}",
                "drawable", context.packageName
            )
        )
        val distance = calculateDistance(
            location.latitude,
            location.longitude,
            city.coordinate.latitude,
            city.coordinate.longitude
        )
        if (distance <= 1) {
            binding.distanceText.text = "Less than one kilometre from you"
        } else {
            binding.distanceText.text = "${distance.toInt()} kilometers from you"
        }

    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val p = 0.017453292519943295    // Math.PI / 180
        val a = 0.5 - cos((lat2 - lat1) * p) / 2 +
                cos(lat1 * p) * cos(lat2 * p) *
                (1 - cos((lon2 - lon1) * p)) / 2

        return 12742 * asin(sqrt(a)) // 2 * R; R = 6371 km
    }
}
