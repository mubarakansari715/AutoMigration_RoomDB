package com.example.automigration_roomdb.utils

import com.example.automigration_roomdb.model.User

interface ItemClick {

    fun clickUserDeleteData(user: User)

    fun clickUserEditData(user: User, position: Int)

}