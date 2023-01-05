package com.meprogrammer.chichmic.di

import android.content.Context
import androidx.room.Room
import com.meprogrammer.chichmic.room.MyDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMyDB(@ApplicationContext context:Context) : MyDB {
        return Room.databaseBuilder(context, MyDB::class.java,"MyDB").build()
    }

}