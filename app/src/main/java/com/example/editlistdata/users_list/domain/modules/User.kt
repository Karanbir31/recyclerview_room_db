package com.example.editlistdata.users_list.domain.modules

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Entity(tableName = "USERS")
data class User  constructor(
    @PrimaryKey val userId: String = "",
    val userProfilePhoto: String = "",
    val userWorkProfile : String = "",
    val userName: String = "",
    val userMobileNumber: Long = 0,
    val userEmail: String = "xyx@test.com",
    val userAddress : String = "Amritsar",
    val userDOB: LocalDate= LocalDate.of(2025, 1, 1)
){

    fun getUserDOBStr(): String{
        return  this.userDOB.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}

class UsersDOBConvertor() {

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String = date.format(DateTimeFormatter.ISO_LOCAL_DATE)

    @TypeConverter
    fun toLocalDate(date: String): LocalDate =
        LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
}