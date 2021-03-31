package com.example.weatherapp.presentation.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.weatherapp.R
import com.example.weatherapp.data.database.WeatherAppDatabase
import com.example.weatherapp.data.location.WeatherAppLocationService
import com.example.weatherapp.data.network.City
import com.example.weatherapp.data.network.CityForForecast
import com.example.weatherapp.data.network.ForecastList
import com.example.weatherapp.databinding.FragmentForecastBinding
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

private const val ARG_PARAM1 = "param1"

class ForecastFragment : Fragment() {

    private lateinit var binding: FragmentForecastBinding
    private lateinit var viewModel: ForecastViewModel
    private lateinit var viewModelFactory: ForecastViewModelFactory
    private lateinit var weatherAppLocationService: WeatherAppLocationService
    private lateinit var adapter: ForecastAdapter

    private var cityParam: City? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cityParam = it.getParcelable<City>(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentForecastBinding.inflate(inflater, container, false)

        binding.progressBar.visibility = View.VISIBLE

        adapter = ForecastAdapter()
        binding.forecast.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            binding.forecast.context,
            DividerItemDecoration.HORIZONTAL
        )

        binding.forecast.addItemDecoration(dividerItemDecoration)

        weatherAppLocationService = WeatherAppLocationService(requireContext(), requireActivity())
        val application = requireNotNull(this.activity).application
        val dataSource = WeatherAppDatabase.getInstance(application).weatherAppDatabaseDao
        viewModelFactory = ForecastViewModelFactory(
            dataSource,
            cityParam!!,
            weatherAppLocationService
        )

        viewModel = ViewModelProvider(this, viewModelFactory).get(ForecastViewModel::class.java)

        viewModel.forecast.observe(viewLifecycleOwner) { forecast ->
            bindForecast(forecast)
        }



        return binding.root
    }

    private fun bindForecast(forecast: ForecastList) {
        binding.dayProcessImage.visibility = View.VISIBLE
        binding.sunriseIcon.visibility = View.VISIBLE
        binding.sunsetIcon.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        setViewProperties(forecast.city)
        adapter.data = forecast.list
        adapter.timeZone = forecast.city.timezone
    }


    private fun setViewProperties(city: CityForForecast) {
        val sunriseDateTime =
            DateTime(city.sunrise * 1000, DateTimeZone.forOffsetMillis(city.timezone * 1000))
        val sunsetDateTime =
            DateTime(city.sunset * 1000, DateTimeZone.forOffsetMillis(city.timezone * 1000))
        binding.apply {
            cityName.text = cityParam!!.name
            sunriseText.text = requireContext().resources.getString(
                R.string.hour_and_minute_date_format,
                sunriseDateTime.hourOfDay().asText,
                sunriseDateTime.minuteOfHour().asText
            )
            sunsetText.text = requireContext().resources.getString(
                R.string.hour_and_minute_date_format,
                sunsetDateTime.hourOfDay().asText,
                sunsetDateTime.minuteOfHour().asText
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: City) =
            ForecastFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }

}