package com.example.editlistdata.edit_user_details.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.editlistdata.R
import com.example.editlistdata.database.UsersDataBase
import com.example.editlistdata.database.UsersDatabaseProvider
import com.example.editlistdata.edit_user_details.domain.EditUserDetailsRepository
import androidx.core.net.toUri

class EditUserDetails : AppCompatActivity() {


    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->

            if (uri != null) {
                this.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                viewModel.updateUserImage(uri = uri)

            } else {
                Toast.makeText(this@EditUserDetails, "unable to select image", Toast.LENGTH_SHORT)
                    .show()
            }

        }

    private lateinit var userIdTextView: TextView
    private lateinit var userNameEditText: EditText
    private lateinit var userImageView: ImageView
    private lateinit var submitButton: Button

    private lateinit var usersDataBase: UsersDataBase
    private lateinit var viewModel: EditUserDetailsViewModel
    private lateinit var repository: EditUserDetailsRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_user_details)
        usersDataBase = UsersDatabaseProvider.getDataBaseInstance(this)
        repository = EditUserDetailsRepository(usersDataBase.usersDao)
        viewModel = EditUserDetailsViewModel(repository)

        userIdTextView = findViewById(R.id.userId)
        userImageView = findViewById(R.id.userProfileImage)
        userNameEditText = findViewById(R.id.userNameEditText)
        submitButton = findViewById(R.id.submitButton)

        val userId = intent.getStringExtra("userId")
        if (!userId.isNullOrEmpty()) {
            viewModel.getUserWithUserID(userId)
        }

        viewModel.user.observe(
            this,
            Observer { user ->

                val userImage = user.userProfilePhoto.toUri()
                Glide.with(this@EditUserDetails).load(user.userProfilePhoto).into(userImageView)

                userIdTextView.text = userId

                userNameEditText.setText(user.userName)
            }
        )


        userImageView.setOnClickListener {
            imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        userNameEditText.setOnClickListener {
            userNameEditText.inputType = InputType.TYPE_CLASS_TEXT
        }


        submitButton.setOnClickListener {
            viewModel.updateUserWithUserId(userNameEditText.text.toString())

            try {
                val resultIntent = Intent().apply {
                    putExtra("userId", userId)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@EditUserDetails, "Unable to go back!!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
}