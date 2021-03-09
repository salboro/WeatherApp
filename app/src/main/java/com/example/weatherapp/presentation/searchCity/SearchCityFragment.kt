package com.example.weatherapp.presentation.searchCity

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.data.database.WeatherAppDatabase
import com.example.weatherapp.data.network.City
import com.example.weatherapp.databinding.FragmentSearchCityBinding


class SearchCityFragment : Fragment() {

    private lateinit var binding: FragmentSearchCityBinding
    private lateinit var viewModel: SearchCityFragmentViewModel
    private lateinit var viewModelFactory: SearchCityFragmentViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchCityBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = WeatherAppDatabase.getInstance(application).weatherAppDatabaseDao
        viewModelFactory = SearchCityFragmentViewModelFactory(dataSource, requireContext())
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(SearchCityFragmentViewModel::class.java)

        viewModel.city.observe(viewLifecycleOwner, Observer {
            onCityClicked(it)
        })

        binding.findButton.setOnClickListener {
            getCityByName()
        }

        binding.findButton.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    getCityByName()
                }
                true
            } else {
                false
            }
        }

        return binding.root
    }

    private fun getCityByName() {
        val cityName = binding.editTextCityName.text.toString()
        viewModel.getCity(cityName)
        binding.findButton.closeKeyboard()
    }

    private fun onCityClicked(city: City) {
        this.findNavController()
            .navigate(SearchCityFragmentDirections.actionSearchCityFragmentToDetailFragment(city))
    }

    private fun View.closeKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}


