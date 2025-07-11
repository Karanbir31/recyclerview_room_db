package com.example.editlistdata.edit_user_details.ui

import android.net.Uri
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.editlistdata.edit_user_details.domain.EditUserDetailsRepository
import com.example.editlistdata.users_list.domain.modules.User
import kotlinx.coroutines.launch
import java.time.LocalDate

class EditUserDetailsViewModel(private val repository: EditUserDetailsRepository) : ViewModel() {
    private val logTag = "EditUserDetailsViewModel"


    private val _user = MutableLiveData(User())
    val user: LiveData<User> = _user

    fun addNewUserData(){
        try {
            viewModelScope.launch {
                repository.addNewUser(_user.value!!)
            }
        }catch (e : Exception){
            Log.e(logTag, "error message : ${e.message}")
        }
    }

    fun getUserWithUserID(userId: Int) {
        try {
            viewModelScope.launch {
                _user.value = repository.getUserWithUserID(userId)
            }

        } catch (e: Exception) {
            Log.e(logTag, "error message : ${e.message}")
        }
    }

    fun saveUpdatedUserDataInRoom() {
        try {
            viewModelScope.launch {
                repository.updateUserWithUserId(_user.value!!)
            }
        } catch (e: Exception) {
            Log.e(logTag, "error message : ${e.message}")
        }
    }


    fun updateUserImage(uri: Uri) {
        val temp = _user.value
        _user.value = temp?.copy(userProfilePhoto = uri.toString())
    }

    fun updateUserDOB(date: LocalDate) {
        val temp = _user.value
        _user.value = temp?.copy(userDOB = date)
    }

    fun updateUserData(
        userWorkProfile : String,
        userName: String,
        userEmail: String,
        userMobileNumber: String,
        userAddress: String
    ) {
        val temp = _user.value
        _user.value = temp?.copy(
            userWorkProfile = userWorkProfile,
            userName =userName,
            userEmail = userEmail,
            userMobileNumber =userMobileNumber.toLong(),
            userAddress = userAddress
        )

    }


    fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validateMobileNumber(numberStr: String): Boolean {
        return numberStr.length == 10 && numberStr.all { it.isDigit() }
    }

    override fun onCleared() {

        saveUpdatedUserDataInRoom()

        super.onCleared()

    }


}