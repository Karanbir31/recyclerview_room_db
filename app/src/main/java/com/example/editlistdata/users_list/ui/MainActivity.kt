package com.example.editlistdata.users_list.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.editlistdata.edit_user_details.ui.EditUserDetails
import com.example.editlistdata.R
import com.example.editlistdata.database.UsersDataBase
import com.example.editlistdata.users_list.domain.UsersRepository
import com.example.editlistdata.users_list.domain.modules.User

class MainActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var usersDataBase: UsersDataBase
    private lateinit var usersRepository: UsersRepository
    private lateinit var usersViewModel: UsersViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userRecyclerView = findViewById(R.id.usersRecyclerView)


        usersViewModel.users.observe(
            this,
            Observer { usersList ->

                userRecyclerView.adapter = UserRcvAdapter(usersList) { userId ->
                    // on click rcv item return user id

                    val intent = Intent(this@MainActivity, EditUserDetails::class.java).apply {
                        putExtra("userId", userId)
                    }
                    startActivity(intent)
                }
            }
        )


    }

    override fun onStart() {
        super.onStart()
    }


}