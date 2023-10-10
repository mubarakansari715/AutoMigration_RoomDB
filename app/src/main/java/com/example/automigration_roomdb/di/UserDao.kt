package com.example.automigration_roomdb.di

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.automigration_roomdb.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("select * from user_data")
    fun getAllUserDataFromRoomDatabase(): List<User>
}