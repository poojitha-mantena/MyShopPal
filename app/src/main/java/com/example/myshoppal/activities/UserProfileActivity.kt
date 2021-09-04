package com.example.myshoppal.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.myshoppal.R
import com.example.myshoppal.activities.models.User
import com.example.myshoppal.firestore.FirestoreClass
import com.example.myshoppal.utils.Constants
import com.example.myshoppal.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.IOException

class UserProfileActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mUserDetails: User
    private var mSelectedImageFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS)){
            mUserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        et_first_name.isEnabled = false
        et_first_name.setText(mUserDetails.firstName)

        et_last_name.isEnabled = false
        et_last_name.setText(mUserDetails.lastName)

        et_email.isEnabled = false
        et_email.setText(mUserDetails.email)

        iv_user_photo.setOnClickListener(this@UserProfileActivity)

        btn_submit.setOnClickListener(this@UserProfileActivity)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.iv_user_photo -> {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Constants.showImageChooser(this)
                    } else {
                        ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE)
                    }
                }

                R.id.btn_submit ->{

                    showProgressDialog(resources.getString(R.string.please_wait))

                    FirestoreClass().uploadImageToCloudStorage(
                        this@UserProfileActivity,
                        mSelectedImageFileUri
                    )

                    if(validateUserProfileDetails()){
                        val userHashMap = HashMap<String, Any>()
                        val mobileNumber = et_mobile_number.text.toString().trim { it <= ' ' }

                        val gender = if (rb_male.isChecked) {
                            Constants.MALE
                        } else {
                            Constants.FEMALE
                        }

                        if (mobileNumber.isNotEmpty()) {
                            userHashMap[Constants.MOBILE] = mobileNumber.toLong()
                        }

                        userHashMap[Constants.GENDER] = gender

                        showProgressDialog(resources.getString(R.string.please_wait))

                        FirestoreClass().updateUserProfileData(
                            this@UserProfileActivity,
                            userHashMap
                        )
                    }
                }
            }
        }


    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Constants.showImageChooser(this)
            } else {
                Toast.makeText(this, resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
            if (data!!.data != null) {
                try {
                    mSelectedImageFileUri = data.data!!
                    GlideLoader(this).loadUserProfile(mSelectedImageFileUri!!,iv_user_photo)

                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(
                        this,
                        getString(R.string.image_selection_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }
    private fun validateUserProfileDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_mobile_number.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(getString(R.string.error_mobile_number), true)
                false
            }
            else -> {
                true
            }
        }
    }

    fun userProfileUpdateSuccess() {

        hideProgressDialog()

        Toast.makeText(
            this@UserProfileActivity,
            resources.getString(R.string.profile_update_success_message),
            Toast.LENGTH_SHORT
        ).show()

        startActivity(Intent(this@UserProfileActivity, MainActivity::class.java))
        finish()
    }

    fun imageUploadSuccess(imageURL: String) {
        hideProgressDialog()

        Toast.makeText(
            this@UserProfileActivity,
            "Your image is uploaded successfully. Image URL is $imageURL",
            Toast.LENGTH_SHORT
        ).show()
    }
}