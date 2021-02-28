package com.example.weatherapp.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.weatherapp.R
import com.example.weatherapp.data.City
import com.example.weatherapp.databinding.FragmentDetailBinding
import com.example.weatherapp.databinding.FragmentListBinding
import com.example.weatherapp.repositories.CityRepository


class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false)

        val city = CityRepository().getCity(DetailFragmentArgs.fromBundle(requireArguments()).cityName)
        binding.city = city
        when (city?.weather?.condition) {
            "snowy" -> binding.conditionImage.setImageResource(R.mipmap.ic_snow_icon_foreground)
            "rainy" -> binding.conditionImage.setImageResource(R.mipmap.ic_rain_icon_foreground)
            "cloudy" -> binding.conditionImage.setImageResource(R.mipmap.ic_cloud_icon_foreground)
            else -> binding.conditionImage.setImageResource(R.mipmap.ic_sun_icon_foreground)
        }

        return binding.root
    }

//    private fun imageChose(binding: FragmentDetailBinding) {
//        when (binding.city.weather.condition) {
//            "snowy" -> binding.conditionImage.setImageResource(R.mipmap.ic_snow_icon_foreground)
//            "rainy" -> binding.conditionImage.setImageResource(R.mipmap.ic_rain_icon_foreground)
//            "cloudy" -> binding.conditionImage.setImageResource(R.mipmap.ic_cloud_icon_foreground)
//            else -> binding.conditionImage.setImageResource(R.mipmap.ic_sun_icon_foreground)
//        }
//
//    }

}