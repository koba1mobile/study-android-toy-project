package com.example.toyproject.controller

import android.util.Log
import androidx.appcompat.widget.SearchView
import com.example.toyproject.api.ApiManager
import com.example.toyproject.api.NetworkState
import com.example.toyproject.model.Repositorys
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView.queryTextChangeEvents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class SearchController() {
    private val TAG = this::class.java.simpleName
    private lateinit var searchSubject: PublishSubject<NetworkState>

    fun searchSubscribe(subject: PublishSubject<NetworkState>) {
        searchSubject = subject
    }

    fun requestSearch(view: SearchView, apiManager: ApiManager): Disposable {
        return queryTextChangeEvents(view)
            .filter { it.isSubmitted }
            .map { it.queryText() }
            .filter { it.isNotEmpty() }
            .map { it.toString() }
            .flatMap { query ->
                apiManager.requestGitRepos(query)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    Repositorys.gitItemRepository.items = it.gitItems
                    searchSubject.onNext(NetworkState.suceess)
                    Log.d(TAG, "total count : ${it.total_count}")
                },
                onError = {
                    searchSubject.onNext(NetworkState.error)
                    Log.e(TAG, "requestSearch error : ${it.message}")
                }
            )
    }
}