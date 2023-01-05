package com.meprogrammer.chichmic.room

import androidx.room.*
import com.meprogrammer.chichmic.model.UserTable

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun saveData(user : UserTable)

    @Transaction
    @Query("SELECT * FROM USERTABLE")
    suspend fun getUser(): UserTable


}