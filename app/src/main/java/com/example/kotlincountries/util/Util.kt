package com.example.kotlincountries.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Options
import com.bumptech.glide.request.RequestOptions
import com.example.kotlincountries.R

fun ImageView.downloadUrl(url:String, placeHolderDrawable: CircularProgressDrawable){

    val options=RequestOptions()
        .placeholder(placeHolderDrawable)
        .error(R.mipmap.ic_launcher_round)


    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)

}
fun placeHolderDrawble(context: Context):CircularProgressDrawable{
    return CircularProgressDrawable(context)
        .apply {
            strokeWidth=8f
            centerRadius=40f
            start()
        }

}
@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView, url: String?){
    url?.let { view.downloadUrl(it, placeHolderDrawble(view.context)) }
}