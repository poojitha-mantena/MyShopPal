package com.example.myshoppal.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.myshoppal.R
import java.io.IOException

class GlideLoader(val context: Context) {

    fun loadUserProfile(imageUri : Uri, imageView : ImageView){
        try {
            Glide
                .with(context)
                .load(imageUri)
                .centerCrop()
                .placeholder(R.drawable.ic_user_placeholder)
                .into(imageView)

        }catch (e : IOException){
            e.printStackTrace()
        }
    }
}