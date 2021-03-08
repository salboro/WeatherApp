package com.example.weatherapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.data.network.City
import com.example.weatherapp.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModelFactory: DetailViewModelFactory
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailBinding.inflate(inflater, container, false)

        viewModelFactory = DetailViewModelFactory(DetailFragmentArgs.fromBundle(requireArguments()).city)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        viewModel.city.observe(viewLifecycleOwner) { city ->
            setViewProperties(city)
        }

        return binding.root
    }

    private fun setViewProperties (city: City) {
        binding.apply {
            cityAndCountryNameText.text = resources.getString(
                R.string.city_and_country_name_format,
                city.name,
                city.country.name
            )
            degrees.text =
                resources.getString(R.string.decrees_format, (city.main.temperature).toInt())
            pressure.text = resources.getString(
                R.string.pressure_format,
                (city.main.pressure * 0.750062).toInt()
            )
            condition.text = resources.getString(
                R.string.condition_and_sediment_format,
                city.weather[0].description,
                city.main.humidity
            )
            binding.conditionImage.setImageResource(
                requireContext().resources.getIdentifier(
                    "ic_${city.weather[0].icon}",
                    "drawable", requireContext().packageName
                )
            )
        }
    }
}