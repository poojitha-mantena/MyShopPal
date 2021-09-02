package com.example.myshoppal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import com.example.myshoppal.R
import com.example.myshoppal.activities.models.User
import com.example.myshoppal.utils.Constants
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        var userDetails: User = User()
        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS)){
            userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        et_first_name.isEnabled = false
        et_first_name.setText(userDetails.firstName)

        et_last_name.isEnabled = false
        et_last_name.setText(userDetails.lastName)

        et_email.isEnabled = false
        et_email.setText(userDetails.email)
    }
}