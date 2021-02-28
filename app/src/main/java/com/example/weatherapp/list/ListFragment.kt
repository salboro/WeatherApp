package com.example.weatherapp.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.City
import com.example.weatherapp.databinding.FragmentListBinding
import com.example.weatherapp.detail.DetailFragment
import com.example.weatherapp.repositories.CityRepository


class ListFragment : Fragment() {

    private lateinit var cityRepository: CityRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list, container, false)


        Log.i("asd", "adsa")
        cityRepository = CityRepository()

        val adapter = CitiesAdapter(CityClickListener { city ->
            onCityClicked(city)
        })
        adapter.data = cityRepository.getCities()
        binding.citiesList.adapter = adapter
        return binding.root
    }

    private fun onCityClicked(city: City) {
        Log.i("asd", "adsa")
        this.findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment(city.name))
    }
}