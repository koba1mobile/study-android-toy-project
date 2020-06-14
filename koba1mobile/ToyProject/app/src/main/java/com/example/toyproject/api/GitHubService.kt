package com.example.toyproject.api

import com.example.toyproject.model.domain.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("search/repositories")
    fun listRepos(@Query("q") query: String): Observable<SearchResponse>
}
