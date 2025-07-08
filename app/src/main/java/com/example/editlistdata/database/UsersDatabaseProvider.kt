package com.example.editlistdata.database

import android.content.Context
import androidx.room.Room

object UsersDatabaseProvider {

    @Volatile
    private var dbInstance : UsersDataBase? =  null

    fun getDataBaseInstance(context: Context) : UsersDataBase{
        if (dbInstance != null){
            return dbInstance!!
        }else synchronized(this){
            val db = Room.databaseBuilder(
                context = context,
                klass = UsersDataBase::class.java,
                name = "USERS_DATABASE"
            ).build()
            dbInstance = db

            return db

        }
    }

}