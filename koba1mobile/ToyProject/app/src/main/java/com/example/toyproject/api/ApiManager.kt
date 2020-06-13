package com.example.toyproject.api

import com.example.toyproject.common.base.ApiUrl
import com.example.toyproject.data.SearchResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    fun requestGitRepos(query: String): Observable<SearchResponse> {
        return Retrofit.Builder()
            .baseUrl(ApiUrl.GitHubUrl.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubService::class.java).listRepos(query)
    }
}