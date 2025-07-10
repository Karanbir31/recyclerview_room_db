package com.example.editlistdata.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.editlistdata.users_list.data.UsersDao
import com.example.editlistdata.users_list.domain.modules.User
import com.example.editlistdata.users_list.domain.modules.UsersDOBConvertor

@Database(entities = [User::class], version = 1)
@TypeConverters(UsersDOBConvertor::class)
abstract class UsersDataBase() : RoomDatabase()  {
    abstract val usersDao : UsersDao

}