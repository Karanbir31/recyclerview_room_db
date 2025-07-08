package com.example.editlistdata.users_list.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.editlistdata.users_list.domain.UsersRepository
import com.example.editlistdata.users_list.domain.modules.User
import kotlinx.coroutines.launch

class UsersViewModel(private val usersRepository: UsersRepository) : ViewModel() {
    private val logTag = "UsersViewModel"

    private val _users = MutableLiveData<List<User>>(emptyList())
    val users: LiveData<List<User>> get() = _users

    init {
        getAllUsers()
    }

    private fun getAllUsers() {
        try {
            viewModelScope.launch {
                val usersList = usersRepository.getAllUsers()
                if (usersList.isEmpty()) {
                    // write static list into room database
                    writeAllUsersData()
                } else {
                    _users.value= usersList
                }
            }
        } catch (e: Exception) {
            Log.e(logTag, "error message : ${e.message}")
        }

    }


    private fun writeAllUsersData() {
        try {
            viewModelScope.launch {
                val users: List<User> = getUsersStaticList()
                usersRepository.writeAllUsersData(users)

                getAllUsers()
            }
        } catch (e: Exception) {
            Log.e(logTag, "error message : ${e.message}")
        }

    }


    private fun getUsersStaticList() : List<User>{
        return listOf(
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "001", userName = "Aarav Sharma"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "002", userName = "Isha Patel"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "003", userName = "Vihaan Gupta"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "004", userName = "Meera Nair"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "006", userName = "Kavya Desai"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "007", userName = "Rohan Mehta"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "005", userName = "Arjun Reddy"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "008", userName = "Tanvi Pillai"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "009", userName = "Dev Arya"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "010", userName = "Saanvi Verma"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "011", userName = "Yash Khanna"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "012", userName = "Anaya Singh"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "013", userName = "Manav Bansal"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "014", userName = "Diya Shetty"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "015", userName = "Krishna Dutta"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "016", userName = "Reyansh Malhotra"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "017", userName = "Aanya Joshi"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "018", userName = "Ishaan Rao"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "019", userName = "Sneha Kapoor"),
            User(userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userId = "020", userName = "Aditya Bajaj")
        )
        // userProfilePhoto = "https://picsum.photos/200"
        //userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg"
        //userProfilePhoto = "https://randomuser.me/api/portraits/women/2.jpg"
    }


}