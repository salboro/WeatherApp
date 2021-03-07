package com.example.weatherapp

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.weatherapp.data.network.IMG_URL

fun bindImage(imgView: ImageView, imgCode: String?) {
    imgCode?.let {
        val imgUrl = IMG_URL.format(imgCode)
        Log.i("img", imgUrl)
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_anim)
                .error(R.drawable.ic_rain_icon_background))
            .into(imgView)
    }
}