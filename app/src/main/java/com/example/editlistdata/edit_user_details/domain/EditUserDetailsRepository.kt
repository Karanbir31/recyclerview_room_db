package com.example.editlistdata.edit_user_details.domain

import com.example.editlistdata.users_list.data.UsersDao
import com.example.editlistdata.users_list.domain.modules.User

class EditUserDetailsRepository(private val usersDao: UsersDao) {

    suspend fun getUserWithUserID(userId: String) = usersDao.getUserWithUserID(userId)

    suspend fun updateUserWithUserId(user: User) = usersDao.updateUserWithUserId(user)

}