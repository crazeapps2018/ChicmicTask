package com.meprogrammer.chichmic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meprogrammer.chichmic.model.UserTable
import com.meprogrammer.chichmic.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {

    val userLiveData get() = mainRepository.userLiveData

    fun saveUser(userTable: UserTable){
        viewModelScope.launch {
            mainRepository.saveUser(userTable)
        }
    }
    fun getUser(){
        viewModelScope.launch {
            mainRepository.getUser()
        }
    }




}