package com.example.toyproject.model

import android.content.Context
import com.example.toyproject.db.DatabaseManager
import com.example.toyproject.model.domain.GitItem
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class GitItemRepository {
    var searchItems: List<GitItem> = arrayListOf()

    var historyItems: List<GitItem> = arrayListOf()

//    fun fetchHistoryItems(context: Context, subject: PublishSubject<DataState>): Disposable =
//        Single.create<List<GitItem>> {
//            DatabaseManager().getHistoryDao(context)
//                .getAll()
//
//        }
//        DatabaseManager().getHistoryDao(context)
//            .getAll()
//            .subscribeOn(Schedulers.io())
//            .map {
//                if(it.){
//                    DataState.empty
//                }else{
//                    DataState.suceess
//                }
//            }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe{ subject.onNext(it) }
}
