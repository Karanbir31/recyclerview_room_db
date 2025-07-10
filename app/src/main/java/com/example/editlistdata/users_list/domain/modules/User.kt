package com.example.editlistdata.users_list.domain.modules

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
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
    val userDOB: LocalDate? = null
)

class UsersDOBConvertor() {

    fun fromLocalDate(date: LocalDate): String = date.format(DateTimeFormatter.ISO_LOCAL_DATE)

    fun toLocalDate(date: String): LocalDate =
        LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
}