package com.example.editlistdata.edit_user_details.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.editlistdata.R
import com.example.editlistdata.database.UsersDataBase
import com.example.editlistdata.database.UsersDatabaseProvider
import com.example.editlistdata.edit_user_details.domain.EditUserDetailsRepository

class EditUserDetails : AppCompatActivity() {

    private lateinit var userIdTextView : TextView

    private lateinit var usersDataBase: UsersDataBase
    private lateinit var viewModel: EditUserDetailsViewModel
    private lateinit var repository: EditUserDetailsRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_user_details)
        usersDataBase = UsersDatabaseProvider.getDataBaseInstance(this)
        repository= EditUserDetailsRepository(usersDataBase.usersDao)
        viewModel = EditUserDetailsViewModel(repository)

        userIdTextView = findViewById(R.id.userId)

        val userId = intent.getStringExtra("userId")
        if (!userId.isNullOrEmpty()){
            viewModel.getUserWithUserID(userId)
        }

        viewModel.user.observe(
            this,
            Observer {user->
                userIdTextView.text = user.userName ?: "user id not available"
            }
        )

    }
}