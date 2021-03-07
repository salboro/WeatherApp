package com.example.weatherapp.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.bindImage
import com.example.weatherapp.data.database.CityWeather
import com.example.weatherapp.data.network.City
import com.example.weatherapp.databinding.CityItemViewBinding
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sqrt

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

    fun bind(cityWeather: City) {
        setViewProperties(cityWeather)
        binding.root.setOnClickListener { onClick(cityWeather) }
    }

    private fun setViewProperties(city: City){
        binding.cityNameText.text = city.name
        binding.countryText.text = city.country.name
        bindImage(binding.conditionImage, city.weather[0].icon)
        val distance = calculateDistance(
            56.488430,
            84.948047,
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
        val p = 0.017453292519943295;    // Math.PI / 180
        val a = 0.5 - cos((lat2 - lat1) * p) /2 +
                cos(lat1 * p) * cos(lat2 * p) *
                (1 - cos((lon2 - lon1) * p))/2;

        return 12742 * asin(sqrt(a)); // 2 * R; R = 6371 km
    }
}
