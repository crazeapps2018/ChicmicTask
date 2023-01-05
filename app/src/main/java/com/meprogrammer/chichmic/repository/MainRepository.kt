package com.meprogrammer.chichmic.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.meprogrammer.chichmic.model.UserTable
import com.meprogrammer.chichmic.room.MyDB
import com.meprogrammer.chichmic.util.NetworkResult
import javax.inject.Inject

class MainRepository @Inject constructor(
    val myDB: MyDB
) {

    private val _userLiveData = MutableLiveData<NetworkResult<UserTable>>()
    val userLiveData: LiveData<NetworkResult<UserTable>>
        get() = _userLiveData

    suspend fun saveUser(userTable: UserTable) {
        _userLiveData.postValue(NetworkResult.Loading())
        myDB.getDao().saveData(userTable)
    }

    suspend fun getUser() {
        val user= myDB.getDao().getUser()
        _userLiveData.postValue(NetworkResult.Success(user))

    }


}