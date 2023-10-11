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

    @Query("select * from user_data order by user_data.id ASC")
    fun getAllUserDataFromRoomDatabase(): List<User>

    @Query("Delete from user_data where user_data.userId = :userId")
    fun deleteUserById(userId: String)

    @Query("select * from user_data where userId = :userId")
    fun getUserId(userId: String): User

    /*@Update
    fun updateUser(user: User)*/
    @Query("update user_data set name = :name where userId = :userId")
    fun updateUser(name: String, userId: String)
}