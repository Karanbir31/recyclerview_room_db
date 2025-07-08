package com.example.editlistdata.edit_user_details.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.editlistdata.edit_user_details.domain.EditUserDetailsRepository
import com.example.editlistdata.users_list.domain.modules.User
import kotlinx.coroutines.launch

class EditUserDetailsViewModel(private val repository: EditUserDetailsRepository) : ViewModel() {
    private val logTag = "EditUserDetailsViewModel"

    private val _user = MutableLiveData<User>(User())
    val user : LiveData<User> = _user



    fun getUserWithUserID(userId: String) {
        try {
            viewModelScope.launch {
              _user.value =  repository.getUserWithUserID(userId)
            }

        } catch (e: Exception) {
            Log.e(logTag, "error message : ${e.message}")
        }

    }

    fun updateUserWithUserId(user: User) {
        try {
            viewModelScope.launch {
                repository.updateUserWithUserId(user)
            }
        } catch (e: Exception) {
            Log.e(logTag, "error message : ${e.message}")
        }

    }




}