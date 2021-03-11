package com.example.weatherapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.data.database.WeatherAppDatabase
import com.example.weatherapp.data.network.City
import com.example.weatherapp.databinding.FragmentDetailBinding
import java.util.*


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModelFactory: DetailViewModelFactory
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = WeatherAppDatabase.getInstance(application).weatherAppDatabaseDao
        viewModelFactory = DetailViewModelFactory(
            dataSource,
            DetailFragmentArgs.fromBundle(requireArguments()).city
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        viewModel.city.observe(viewLifecycleOwner) { city ->
            setViewProperties(city)
        }

        viewModel.isFavoriteCity.observe(viewLifecycleOwner, Observer {
            setIsFavorite(it)
        })

        return binding.root
    }

    private fun setViewProperties (city: City) {
        binding.apply {
            cityInfoText.text = resources.getString(
                R.string.city_and_country_name_format,
                city.name,
                city.country.name
            )
            temperatureText.text =
                resources.getString(R.string.decrees_format, (city.main.temperature).toInt())
            realFeelTemp.text =
                resources.getString(R.string.real_feels_format, (city.main.feelsLike).toInt())
            pressureText.text = resources.getString(
                R.string.pressure_format,
                (city.main.pressure * 0.750062).toInt()
            )
            weatherDescriptionText.text = city.weather[0].description
            weatherIcon.setImageResource(
                requireContext().resources.getIdentifier(
                    "ic_${city.weather[0].icon}",
                    "drawable", requireContext().packageName
                )
            )
            windSpeedText.text =
                requireContext().getString(R.string.wind_speed_format, city.wind.speed.toInt())
            cloudinessText.text =
                requireContext().getString(R.string.cloudiness_format, city.clouds.cloudiness)
            humidityText.text =
                requireContext().getString(R.string.cloudiness_format, city.main.humidity)
            when {
                city.rain != null -> {
                    sidenmentText.text =
                        requireContext().getString(R.string.rain_format, city.rain.forOneHour)
                }
                city.snow != null -> {
                    sidenmentText.text =
                        requireContext().getString(R.string.snow_format, city.snow.forOneHour)
                }
                else -> {
                    sidenmentText.text = requireContext().getString(R.string.null_precipitation)
                }
            }


            val cloudList = listOf("clouds", "few clouds", "scattered clouds", "broken clouds")
            val rainList = listOf("rain", "shower rain")
            weatherImage.setImageResource(
                when (city.weather[0].main.toLowerCase(Locale.ROOT)) {
                    "clear" -> R.drawable.clear_sky
                    in cloudList -> R.drawable.cloud
                    in rainList -> R.drawable.rain
                    "thunderstorm" -> R.drawable.thunderstorm
                    "snow" -> R.drawable.snow
                    else -> R.drawable.mist
                }
            )



            isFavoriteImage.setOnClickListener {
                viewModel.setFavoriteCityCondition()
            }
        }
    }

    private fun setIsFavorite(isFavoriteCity: Boolean) {
        if (isFavoriteCity) {
            binding.isFavoriteImage.setImageResource(R.drawable.ic_star_fill)
        } else {
            binding.isFavoriteImage.setImageResource(R.drawable.ic_star)
        }
    }
}