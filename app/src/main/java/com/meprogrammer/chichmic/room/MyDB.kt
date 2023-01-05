package com.meprogrammer.chichmic.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meprogrammer.chichmic.model.UserTable

@Database(entities = [UserTable::class], version = 1, exportSchema = false)
abstract class MyDB : RoomDatabase() {

    abstract fun getDao() : MyDao
}