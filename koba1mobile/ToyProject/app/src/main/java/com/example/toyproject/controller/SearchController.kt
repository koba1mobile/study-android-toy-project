package com.example.toyproject.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.example.toyproject.api.ApiManager
import com.example.toyproject.api.NetworkState
import com.example.toyproject.model.Repositorys
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView.queryTextChangeEvents
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class SearchController(val context: Context?) {
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
            .flatMap {
                if(it.total_count == 0){
                    Observable.error(IllegalStateException("검색 결과 없습니다."))
                }else {
                    Observable.just(it)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { Toast.makeText(context, "total repo count : ${it.total_count}", Toast.LENGTH_SHORT).show() }
            .doOnError { Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show() }
            .subscribeBy(
                onNext = {
                    Repositorys.gitItemRepository.items = it.gitItems
                    searchSubject.onNext(NetworkState.suceess)
                },
                onError = {
                    searchSubject.onNext(NetworkState.error)
                }
            )
    }
}