package com.example.editlistdata.users_list.domain.modules

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "USERS")
data class User(
    val userProfilePhoto : String = "",
   @PrimaryKey val userId : String = "",
    val userName : String = ""
)
