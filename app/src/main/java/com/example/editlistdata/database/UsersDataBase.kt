package com.example.editlistdata.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.editlistdata.users_list.data.UsersDao
import com.example.editlistdata.users_list.domain.modules.User

@Database(entities = [User::class], version = 1)
abstract class UsersDataBase() : RoomDatabase()  {
    abstract val usersDao : UsersDao

}