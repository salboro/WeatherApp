package com.example.weatherapp.screen

import android.view.View
import com.agoda.kakao.bottomnav.KBottomNavigationView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.example.weatherapp.R
import org.hamcrest.Matcher

object FavoriteListScreen : Screen<FavoriteListScreen>() {

    val favoriteCitiesList = KRecyclerView(
        builder = { withId(R.id.favorite_cities_list) },
        itemTypeBuilder = { itemType(::FavoriteCityItem) }
    )

    val bottomNavigationMenu = KBottomNavigationView { withId(R.id.bottom_nav) }

    class FavoriteCityItem(parent: Matcher<View>) : KRecyclerItem<FavoriteCityItem>(parent) {
        val cityName = KTextView(parent) { withId(R.id.cityNameText) }
    }
}