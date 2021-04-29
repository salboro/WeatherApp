package com.example.weatherapp.screen

import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.example.weatherapp.R
import org.hamcrest.Matcher

object NearListScreen : Screen<NearListScreen>() {

    val citiesList = KRecyclerView(
        builder = { withId(R.id.cities_list) },
        itemTypeBuilder = { itemType(::CityItem) }
    )

    class CityItem(parent: Matcher<View>) : KRecyclerItem<CityItem>(parent) {
        val cityName = KTextView(parent) { withId(R.id.cityNameText) }
        val countryName = KTextView(parent) { withId(R.id.countryText) }
        val distance = KTextView(parent) { withId(R.id.distanceText) }
    }
}