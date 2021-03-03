package com.example.weatherapp.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.domain.City
import com.example.weatherapp.databinding.FragmentListBinding
import com.example.weatherapp.domain.CityRepository


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
        viewModelFactory = ListViewModelFactory()
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