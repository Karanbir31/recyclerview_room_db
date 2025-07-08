package com.example.editlistdata.edit_user_details.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.editlistdata.R

class EditUserDetails : AppCompatActivity() {

    private lateinit var userIdTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_user_details)

        userIdTextView = findViewById(R.id.userId)

        val userId = intent.getStringExtra("userId")

        userIdTextView.text = userId ?: "user id not available"

    }
}