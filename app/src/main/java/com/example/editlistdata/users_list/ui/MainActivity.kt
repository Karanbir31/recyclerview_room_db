package com.example.editlistdata.users_list.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.editlistdata.R
import com.example.editlistdata.database.UsersDataBase
import com.example.editlistdata.database.UsersDatabaseProvider
import com.example.editlistdata.edit_user_details.ui.EditUserDetails
import com.example.editlistdata.users_list.domain.UsersRepository
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data?.getStringExtra("userId")
            // Handle result
            Toast.makeText(this@MainActivity, "User $data is updated", Toast.LENGTH_SHORT).show()
        }
    }


    private lateinit var userRecyclerView: RecyclerView
    private lateinit var toolbar: MaterialToolbar
    private lateinit var addNewUserFloatingActionButton: FloatingActionButton

    private lateinit var usersDataBase: UsersDataBase
    private lateinit var usersRepository: UsersRepository
    private lateinit var usersViewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userRecyclerView = findViewById(R.id.usersRecyclerView)
        toolbar = findViewById(R.id.toolbar)
        addNewUserFloatingActionButton = findViewById(R.id.addNewUserFloatingActionBtn)

        setSupportActionBar(toolbar)


        usersDataBase = UsersDatabaseProvider.getDataBaseInstance(this)
        usersRepository = UsersRepository(usersDataBase.usersDao)
        usersViewModel = UsersViewModel(usersRepository)

        usersViewModel.users.observe(
            this,
            Observer { usersList ->
                Log.d("mainActivity", "userList size ${usersList.size}")

                userRecyclerView.adapter = UserRcvAdapter(usersList) { userId ->
                    // on click rcv item return user id
                    navigateToEditUserActivity(userId)
                }
            }
        )

        addNewUserFloatingActionButton.setOnClickListener {

            navigateToEditUserActivity(userId = 0)

        }


    }


    private fun navigateToEditUserActivity(userId: Int) {
        try {
            val intent = Intent(this@MainActivity, EditUserDetails::class.java).apply {
                putExtra("userId", userId)
            }
            resultLauncher.launch(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this@MainActivity,
                "Unable to open Edit User details Screen",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onStart() {
        super.onStart()

        usersViewModel.getAllUsers()
    }

}