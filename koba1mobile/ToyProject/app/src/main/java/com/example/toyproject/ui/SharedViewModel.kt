package com.example.toyproject.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toyproject.model.domain.GitItem

class SharedViewModel : ViewModel() {
    private val TAG = this::class.java.simpleName

    val itemLiveData: MutableLiveData<GitItem> by lazy{ MutableLiveData<GitItem>() }

    fun selectOwner(item: GitItem){
        itemLiveData.value = item
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared")
    }


}