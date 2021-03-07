package com.example.weatherapp.presentation.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.data.database.CityWeather
import com.example.weatherapp.data.database.WeatherAppDatabase
import com.example.weatherapp.data.network.City
import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.data.network.WeatherApiService
import com.example.weatherapp.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel: ListViewModel
    private lateinit var viewModelFactory: ListViewModelFactory
    private val adapter = CitiesAdapter(::onCityClicked)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = WeatherAppDatabase.getInstance(application).weatherAppDatabaseDao
        viewModelFactory = ListViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ListViewModel::class.java)

        viewModel.cityList.observe(viewLifecycleOwner) { cityList ->
            bindCitiesList(cityList)
        }

        binding.citiesList.adapter = adapter
        return binding.root
    }

    private fun bindCitiesList(list: List<City>) {
        adapter.data = list
    }

    private fun onCityClicked(city: City) {
        this.findNavController()
            .navigate(ListFragmentDirections.actionListFragmentToDetailFragment(city))
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCities()
    }
}