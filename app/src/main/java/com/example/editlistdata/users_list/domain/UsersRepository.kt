package com.example.editlistdata.users_list.domain

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.editlistdata.users_list.data.UsersDao
import com.example.editlistdata.users_list.domain.modules.User

class UsersRepository( private val usersDao: UsersDao) {

    suspend fun getAllUsers()= usersDao.getAllUsers()

    suspend fun writeAllUsersData(users: List<User>) = usersDao.writeAllUsersData(users)


}