package com.example.editlistdata.users_list.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.editlistdata.users_list.domain.modules.User

@Dao
interface UsersDao {

    @Query("SELECT * FROM USERS")
    suspend fun getAllUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addNewUser(user : User)

    @Query("SELECT * FROM USERS WHERE userId = :userId ")
    suspend fun getUserWithUserID(userId: Int): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun writeAllUsersData(users: List<User>)

    @Update
    suspend fun updateUserWithUserId(user: User)


}