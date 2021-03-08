//package com.example.weatherapp.presentation.favoriteCityList
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.fragment.findNavController
//import com.example.weatherapp.data.database.WeatherAppDatabase
//import com.example.weatherapp.data.network.City
//import com.example.weatherapp.databinding.FragmentListNearBinding
//import com.example.weatherapp.presentation.nearCityList.CitiesAdapter
//import com.example.weatherapp.presentation.nearCityList.NearCityListViewModel
//import com.example.weatherapp.presentation.nearCityList.NearCityListViewModelFactory
//
//class FavoriteCityListFragment : Fragment() {
//
//    private val REQUEST_LOCATION_PERMISSION = 1
//
//    private var isMyLocationEnable: Boolean = false
//
//    private lateinit var binding: FragmentListNearBinding
//    private lateinit var viewModelNearCity: NearCityListViewModel
//    private lateinit var viewModelFactory: NearCityListViewModelFactory
//    private lateinit var adapter: CitiesAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentListNearBinding.inflate(inflater, container, false)
//        val application = requireNotNull(this.activity).application
//        val dataSource = WeatherAppDatabase.getInstance(application).weatherAppDatabaseDao
//        viewModelFactory = NearCityListViewModelFactory(dataSource)
//        viewModelNearCity = ViewModelProvider(this, viewModelFactory).get(NearCityListViewModel::class.java)
//        adapter = CitiesAdapter(::onCityClicked)
//
//
//        return binding.root
//    }
//
//
//    private fun bindCitiesList(list: List<City>) {
//        adapter.data = list
//    }
//
//    private fun onCityClicked(city: City) {
//        this.findNavController()
//            .navigate(ListFragmentDirections.actionListFragmentToDetailFragment(city))
//    }
//}