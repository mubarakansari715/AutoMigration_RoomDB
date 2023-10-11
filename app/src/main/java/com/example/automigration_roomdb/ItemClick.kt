package com.example.automigration_roomdb

import com.example.automigration_roomdb.model.User

interface ItemClick {

    fun itemClickListener(user: User)

    fun itemClickDeleteUser(user: User, position: Int)
}