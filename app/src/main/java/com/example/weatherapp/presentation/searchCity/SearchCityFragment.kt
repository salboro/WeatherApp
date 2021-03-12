package com.example.weatherapp.presentation.searchCity

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.data.database.WeatherAppDatabase
import com.example.weatherapp.data.location.WeatherAppLocationService
import com.example.weatherapp.data.network.City
import com.example.weatherapp.databinding.FragmentSearchCityBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class SearchCityFragment : Fragment() {

    private lateinit var binding: FragmentSearchCityBinding
    private lateinit var viewModel: SearchCityFragmentViewModel
    private lateinit var viewModelFactory: SearchCityFragmentViewModelFactory
    private lateinit var weatherAppLocationService: WeatherAppLocationService
    private var clickCounter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchCityBinding.inflate(inflater, container, false)

        this.activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.visibility =
            View.VISIBLE

        weatherAppLocationService = WeatherAppLocationService(requireContext(), requireActivity())

        val application = requireNotNull(this.activity).application
        val dataSource = WeatherAppDatabase.getInstance(application).weatherAppDatabaseDao

        viewModelFactory = SearchCityFragmentViewModelFactory(
            dataSource,
            requireContext(),
            weatherAppLocationService
        )
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

        binding.imageView.setOnClickListener {
            sunRotate()
        }

        return binding.root
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            Toast.makeText(requireContext(), "keyboard visible", Toast.LENGTH_SHORT).show();
        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            Toast.makeText(requireContext(), "keyboard hidden", Toast.LENGTH_SHORT).show();
        }
    }

    private fun sunRotate() {
        if (clickCounter == 10) {
            val animator = ObjectAnimator.ofFloat(binding.imageView, View.ROTATION, 360f, 0f)
            animator.duration = 10000
            animator.repeatCount = Animation.INFINITE
            animator.interpolator = LinearInterpolator()
            animator.start()
        } else {
            clickCounter++
        }
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

    override fun onResume() {
        super.onResume()
        clickCounter = 0
    }

    private fun View.closeKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}


