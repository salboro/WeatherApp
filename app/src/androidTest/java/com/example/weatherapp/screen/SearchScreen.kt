package com.example.weatherapp.screen

import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.example.weatherapp.R

object SearchScreen : Screen<SearchScreen>() {

    val cityNameEditField = KEditText { withId(R.id.editTextCityName) }

    val submitButton = KButton { withId(R.id.findButton) }
}