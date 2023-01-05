package com.meprogrammer.chichmic.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
 class UserTable (

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 1,

    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var phone: String = "",
    var gender: String = "",
    var dob: String = "",
    var image: String = "",

)