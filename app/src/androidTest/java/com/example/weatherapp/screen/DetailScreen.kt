package com.example.weatherapp.screen

import com.agoda.kakao.image.KImageView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.example.weatherapp.R

object DetailScreen : Screen<DetailScreen>() {

    val cityInfo = KTextView { withId(R.id.cityInfoText) }

    val isFavoriteImage = KImageView { withId(R.id.isFavoriteImage) }

    val navigateUpButton = KImageView { withContentDescription("Navigate up") }
}