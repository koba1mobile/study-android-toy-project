package com.example.toyproject.controller

import android.content.Context
import com.example.toyproject.model.DataState
import com.example.toyproject.model.Repositorys
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

class HistoryController(private val context: Context) {
    fun fetchHistoryItems(dataSubject: PublishSubject<DataState>): Disposable =
        Repositorys.gitItemRepository.fetchHistoryItems(context, dataSubject)
}