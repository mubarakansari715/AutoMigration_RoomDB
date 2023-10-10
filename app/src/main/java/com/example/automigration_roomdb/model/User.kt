package com.example.automigration_roomdb.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_data")
data class User(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var name: String? = ""
)