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

    fun getUserWithUserID(userId: String) {
        try {
            viewModelScope.launch {
                _user.value = repository.getUserWithUserID(userId)
            }

        } catch (e: Exception) {
            Log.e(logTag, "error message : ${e.message}")
        }
    }

    fun updateUserWithUserId(updatedName: String) {
        try {
            viewModelScope.launch {
                val updatedUserDetails = _user.value?.copy(userName = updatedName)
                repository.updateUserWithUserId(updatedUserDetails!!)
            }
        } catch (e: Exception) {
            Log.e(logTag, "error message : ${e.message}")
        }
    }


    fun updateUserImage(uri: Uri) {
        val temp = _user.value
        _user.value = temp?.copy(userProfilePhoto = uri.toString())
    }

    fun updateUserName(name: String) {
        val temp = _user.value
        _user.value = temp?.copy(userName = name)
    }

    fun updateUserDOB(date: LocalDate) {
        val temp = _user.value
        _user.value = temp?.copy(userDOB = date)
    }

    fun updateUserMobileNumber(number: String) {
        val temp = _user.value
        _user.value = temp?.copy(userMobileNumber = number.toLong())
    }

    fun updateUserEmail(email: String) {
        val temp = _user.value
        _user.value = temp?.copy(userEmail = email)
    }

    fun updateUserAddress(address: String) {
        val temp = _user.value
        _user.value = temp?.copy(userAddress = address)
    }



    fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validateMobileNumber(numberStr: String): Boolean {
        return numberStr.length == 10 && numberStr.all { it.isDigit() }
    }


}