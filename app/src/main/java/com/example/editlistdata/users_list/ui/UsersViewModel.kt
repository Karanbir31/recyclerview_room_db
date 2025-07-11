package com.example.editlistdata.users_list.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.editlistdata.users_list.domain.UsersRepository
import com.example.editlistdata.users_list.domain.modules.User
import kotlinx.coroutines.launch
import java.time.LocalDate

class UsersViewModel(private val usersRepository: UsersRepository) : ViewModel() {
    private val logTag = "UsersViewModel"

    private val _users = MutableLiveData<List<User>>(emptyList())
    val users: LiveData<List<User>> get() = _users

    init {
        Log.d("UsersViewModel", "Users ViewModel Instance is created")
        getAllUsers()
    }

    fun getAllUsers() {
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


    private fun getUsersStaticList(): List<User> {
        return listOf(
            User(userId = 1, userName = "Aarav Sharma", userProfilePhoto = "https://randomuser.me/api/portraits/men/1.jpg", userWorkProfile = "Android Developer", userAddress = "Delhi", userMobileNumber = 9876543210, userEmail = "aarav.sharma@example.com", userDOB = LocalDate.of(1996, 3, 18)),
            User(userId = 2, userName = "Isha Verma", userProfilePhoto = "https://randomuser.me/api/portraits/women/2.jpg", userWorkProfile = "UI/UX Designer", userAddress = "Mumbai", userMobileNumber = 9876543211, userEmail = "isha.verma@example.com", userDOB = LocalDate.of(1998, 7, 12)),
            User(userId = 3, userName = "Rohan Mehta", userProfilePhoto = "https://randomuser.me/api/portraits/men/3.jpg", userWorkProfile = "Backend Engineer", userAddress = "Bengaluru", userMobileNumber = 9876543212, userEmail = "rohan.mehta@example.com", userDOB = LocalDate.of(1992, 11, 5)),
            User(userId = 4, userName = "Kavya Nair", userProfilePhoto = "https://randomuser.me/api/portraits/women/4.jpg", userWorkProfile = "Product Manager", userAddress = "Chennai", userMobileNumber = 9876543213, userEmail = "kavya.nair@example.com", userDOB = LocalDate.of(1995, 1, 22)),
            User(userId = 5, userName = "Vihaan Gupta", userProfilePhoto = "https://randomuser.me/api/portraits/men/5.jpg", userWorkProfile = "QA Analyst", userAddress = "Hyderabad", userMobileNumber = 9876543214, userEmail = "vihaan.gupta@example.com", userDOB = LocalDate.of(1994, 4, 30)),
            User(userId = 6, userName = "Meera Joshi", userProfilePhoto = "https://randomuser.me/api/portraits/women/6.jpg", userWorkProfile = "Data Scientist", userAddress = "Kolkata", userMobileNumber = 9876543215, userEmail = "meera.joshi@example.com", userDOB = LocalDate.of(1997, 8, 9)),
            User(userId = 7, userName = "Aditya Reddy", userProfilePhoto = "https://randomuser.me/api/portraits/men/7.jpg", userWorkProfile = "DevOps Engineer", userAddress = "Pune", userMobileNumber = 9876543216, userEmail = "aditya.reddy@example.com", userDOB = LocalDate.of(1993, 6, 14)),
            User(userId = 8, userName = "Sneha Pillai", userProfilePhoto = "https://randomuser.me/api/portraits/women/8.jpg", userWorkProfile = "Marketing Strategist", userAddress = "Amritsar", userMobileNumber = 9876543217, userEmail = "sneha.pillai@example.com", userDOB = LocalDate.of(1990, 12, 27)),
            User(userId = 9, userName = "Yash Kapoor", userProfilePhoto = "https://randomuser.me/api/portraits/men/9.jpg", userWorkProfile = "Frontend Developer", userAddress = "Lucknow", userMobileNumber = 9876543218, userEmail = "yash.kapoor@example.com", userDOB = LocalDate.of(1999, 2, 17)),
            User(userId = 0, userName = "Anaya Singh", userProfilePhoto = "https://randomuser.me/api/portraits/women/10.jpg", userWorkProfile = "Graphic Designer", userAddress = "Jaipur", userMobileNumber = 9876543219, userEmail = "anaya.singh@example.com", userDOB = LocalDate.of(1996, 10, 4))
        )
    }


}