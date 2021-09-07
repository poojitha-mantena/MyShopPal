package com.example.myshoppal.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constants {
    const val USERS: String = "users"
    const val PRODUCTS: String = "products"

    const val MY_SHOP_PAL_PREFERENCES: String = "myShopPalPreferences"
    const val LOGGED_IN_USERNAME: String = "logged_in_user_name"
    const val EXTRA_USER_DETAILS: String = "extra_user_details"
    const val READ_STORAGE_PERMISSION_CODE = 2
    const val PICK_IMAGE_REQUEST_CODE = 1

    const val MALE: String = "male"
    const val FEMALE: String = "female"
    const val FIRST_NAME: String = "firstName"
    const val LAST_NAME: String = "lastName"
    const val USER_PROFILE_IMAGE: String = "User_Profile_Image"

    const val IMAGE: String = "image"
    const val MOBILE: String = "mobile"
    const val GENDER: String = "gender"
    const val COMPLETE_PROFILE: String = "profileCompleted"

    const val PRODUCT_IMAGE: String = "Product_Image"



    fun showImageChooser(activity: Activity) {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(activity: Activity,uri : Uri?) : String?{

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(activity.contentResolver.getType(uri!!))

    }

}