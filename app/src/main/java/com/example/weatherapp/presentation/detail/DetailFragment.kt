package com.example.weatherapp.presentation.detail

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.data.database.CityWeather
import com.example.weatherapp.databinding.FragmentDetailBinding
import com.example.weatherapp.data.database.WeatherAppDatabase


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
        viewModelFactory = DetailViewModelFactory(dataSource, DetailFragmentArgs.fromBundle(requireArguments()).cityId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        viewModel.city.observe(viewLifecycleOwner) { city ->
            setViewProperties(city)
        }
        return binding.root
    }

    private fun setViewProperties (city: CityWeather) {
        binding.apply {
            cityAndCountryNameText.text = resources.getString(R.string.city_and_country_name_format, city.name, city.country )
            degrees.text = resources.getString(R.string.decrees_format, city.degreesC)
            pressure.text = resources.getString(R.string.pressure_format, city.pressure)
            condition.text = resources.getString(R.string.condition_and_sediment_format, city.condition, city.sediment)
        }

        when (city.condition) {
            "snowy" -> binding.conditionImage.setImageResource(R.mipmap.ic_snow_icon_foreground)
            "rainy" -> binding.conditionImage.setImageResource(R.mipmap.ic_rain_icon_foreground)
            "cloudy" -> binding.conditionImage.setImageResource(R.mipmap.ic_only_cloud_icon_foreground)
            else -> binding.conditionImage.setImageResource(R.mipmap.ic_sun_icon_foreground)
        }

        binding.sunForCloud.visibility = View.GONE

        animate()
    }

    private fun animate() {
        Log.i("condition", viewModel.city.value?.condition.toString())
        when (viewModel.city.value?.condition.toString()){
            "sunny" -> {
                Log.i("condition", viewModel.city.value?.condition.toString())
                val animator = ObjectAnimator.ofFloat(binding.conditionImage, View.ROTATION, 360f, 0f)
                animator.duration = 20000
                animator.repeatCount = Animation.INFINITE
                animator.interpolator = LinearInterpolator()
                animator.start()
            }
            "cloudy" -> {
                Log.i("condition", viewModel.city.value?.condition.toString())
                binding.sunForCloud.visibility = View.VISIBLE
                val animator = ObjectAnimator.ofFloat(binding.sunForCloud, View.ROTATION, 360f, 0f)
                animator.duration = 20000
                animator.repeatCount = Animation.INFINITE
                animator.interpolator = LinearInterpolator()
                animator.start()
            }
        }
    }
}