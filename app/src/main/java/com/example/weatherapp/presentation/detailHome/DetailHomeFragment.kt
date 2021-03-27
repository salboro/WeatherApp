package com.example.weatherapp.presentation.detailHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weatherapp.R
import com.example.weatherapp.data.network.City
import com.example.weatherapp.databinding.FragmentDetailHomeBinding
import com.example.weatherapp.presentation.detail.DetailFragment
import com.example.weatherapp.presentation.forecast.ForecastFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val NUM_PAGES = 2


class DetailHomeFragment : Fragment() {

    private lateinit var binding: FragmentDetailHomeBinding
    private lateinit var city: City

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        this.activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.visibility = View.GONE

        binding = FragmentDetailHomeBinding.inflate(layoutInflater, container, false)

        city = DetailHomeFragmentArgs.fromBundle(requireArguments()).city

        val pagerAdapter = ScreenSlidePagerAdapter(this)

        binding.pager.adapter = pagerAdapter
//        binding.pager.setPageTransformer(ZoomOutPageTransformer())

        return binding.root
    }


    private inner class ScreenSlidePagerAdapter(f: Fragment) : FragmentStateAdapter(f) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> DetailFragment.newInstance(city)
                1 -> ForecastFragment.newInstance(city)
                else -> throw ExceptionInInitializerError("There's no page with that num!")
            }
        }

    }

}