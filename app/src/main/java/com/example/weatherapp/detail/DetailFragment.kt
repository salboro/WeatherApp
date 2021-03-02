package com.example.weatherapp.detail

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.data.City
import com.example.weatherapp.databinding.FragmentDetailBinding
import com.example.weatherapp.repositories.CityRepository


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModelFactory: DetailViewModelFactory
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        viewModelFactory = DetailViewModelFactory(CityRepository(), DetailFragmentArgs.fromBundle(requireArguments()).cityName)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        setViewProperties()

        return binding.root
    }

    private fun setViewProperties () {
        val city = viewModel.city.value
        if (city != null) {
            binding.apply {
                cityNameText.text = city.name
                countryText.text = city.country
                degrees.text = resources.getString(R.string.decrees_format, city.weather.degreesC)
                pressure.text = resources.getString(R.string.pressure_format, city.weather.pressure)
                condition.text = resources.getString(R.string.condition_and_sediment_format, city.weather.condition, city.weather.sediment)
            }

            when (city.weather.condition) {
                "snowy" -> binding.conditionImage.setImageResource(R.mipmap.ic_snow_icon_foreground)
                "rainy" -> binding.conditionImage.setImageResource(R.mipmap.ic_rain_icon_foreground)
                "cloudy" -> binding.conditionImage.setImageResource(R.mipmap.ic_only_cloud_icon_foreground)
                else -> binding.conditionImage.setImageResource(R.mipmap.ic_sun_icon_foreground)
            }
        }
    }

    private fun rotate() {
        when (viewModel.city.value?.weather?.condition){
            "sunny" -> {
                val animator = ObjectAnimator.ofFloat(binding.conditionImage, View.ROTATION, 360f, 0f)
                animator.duration = 10000
                animator.repeatCount = Animation.INFINITE
                animator.interpolator = LinearInterpolator()
                animator.start()
            }
            "cloudy" -> {
                binding.sunForCloud.visibility = View.VISIBLE
                val animator = ObjectAnimator.ofFloat(binding.sunForCloud, View.ROTATION, 360f, 0f)
                animator.duration = 10000
                animator.repeatCount = Animation.INFINITE
                animator.interpolator = LinearInterpolator()
                animator.start()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.sunForCloud.visibility = View.GONE
        rotate()
    }

}