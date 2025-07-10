package com.example.editlistdata.edit_user_details.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
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
import java.time.LocalDate
import java.util.Calendar


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

    private lateinit var submitButton : Button
    private lateinit var userImageView: ImageView
    private lateinit var userWorkProfileEditText: TextView
    private lateinit var userNameEditText: EditText
    private lateinit var userMobileNumberEditText: EditText
    private lateinit var userEmailEditText: EditText
    private lateinit var userAddressEditText: EditText
    private lateinit var userDOBText: TextView

    private lateinit var usersDataBase: UsersDataBase
    private lateinit var viewModel: EditUserDetailsViewModel
    private lateinit var repository: EditUserDetailsRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_user_details)
        usersDataBase = UsersDatabaseProvider.getDataBaseInstance(this)
        repository = EditUserDetailsRepository(usersDataBase.usersDao)
        viewModel = EditUserDetailsViewModel(repository)

        submitButton = findViewById(R.id.submitButton)

        userImageView = findViewById(R.id.userProfileImage)
        userWorkProfileEditText = findViewById(R.id.userWorkProfileText)

        userNameEditText = findViewById(R.id.userNameEditText)
        userDOBText = findViewById(R.id.userDOBText)
        userMobileNumberEditText = findViewById(R.id.userMobileNumberEditText)
        userEmailEditText = findViewById(R.id.userEmailEditText)
        userAddressEditText = findViewById(R.id.userAddressEditText)


        val userId = intent.getStringExtra("userId")
        if (!userId.isNullOrEmpty()) {
            viewModel.getUserWithUserID(userId)
        }

        // select user date of birth using date picker dialog
        userDOBText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this@EditUserDetails, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
                viewModel.updateUserDOB(selectedDate)
            }, year, month, day).show()
        }


        viewModel.user.observe(
            this,
            Observer { user ->

                val userImage = user.userProfilePhoto.toUri()
                Glide.with(this@EditUserDetails).load(user.userProfilePhoto).into(userImageView)

                userWorkProfileEditText.text = user.userWorkProfile

                userNameEditText.setText(user.userName)

                userDOBText.text = user.getUserDOBStr()

                userMobileNumberEditText.setText(user.userMobileNumber.toString())

                userEmailEditText.setText(user.userEmail)

                userAddressEditText.setText(user.userAddress)

            }
        )


        userImageView.setOnClickListener {
            imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        submitButton.setOnClickListener {
            val email = userEmailEditText.text.toString()
            val mobileNumber = userMobileNumberEditText.text.toString()

            if(viewModel.validateEmail(email) && viewModel.validateMobileNumber(mobileNumber)){
                viewModel.updateUserWithUserId(userNameEditText.text.toString())
            }
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