package com.example.weatherapp.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.City
import com.example.weatherapp.databinding.FragmentListBinding
import com.example.weatherapp.detail.DetailFragment
import com.example.weatherapp.repositories.CityRepository


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
        viewModelFactory = ListViewModelFactory(CityRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(ListViewModel::class.java)
        viewModel.cityList.observe(viewLifecycleOwner) { cityList ->
            bindCitiesList(cityList)
        }
        binding.citiesList.adapter = adapter
        return binding.root
    }

    fun bindCitiesList(list: List<City>) {
        adapter.data = list
    }

    private fun onCityClicked(city: City) {
        this.findNavController()
            .navigate(ListFragmentDirections.actionListFragmentToDetailFragment(city.name))
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCities()
    }
}