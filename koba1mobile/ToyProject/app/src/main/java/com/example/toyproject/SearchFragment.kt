package com.example.toyproject

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.example.toyproject.api.ApiManager
import com.example.toyproject.common.base.BaseFragment
import com.jakewharton.rxbinding2.support.v7.widget.queryTextChangeEvents
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SearchFragment : BaseFragment() {
    private lateinit var apiManager: ApiManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        init()
    }

    private fun initView() {
        // toolbar에 back버튼 생성
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // onCreateOptionsMenu 실행되도록
        setHasOptionsMenu(true)
    }

    private fun init() {
        apiManager = ApiManager()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem: MenuItem = menu.findItem(R.id.search_view)

        // fragment 진입하면 SearchView 바로 활성화
        searchItem.expandActionView()

        with(searchItem.actionView as SearchView) {
            compositeDisposable += queryTextChangeEvents()
                .filter { it.isSubmitted }
                .map { it.queryText() }
                .filter { it.isNotEmpty() }
                .map { it.toString() }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { error -> println(error.message) }
                .subscribe { query ->
                    run {
                        requestSearch(query)
                        this.clearFocus()
                    }
                }
        }
    }

    private fun requestSearch(query: String) {
        Log.d(TAG, "request query: $query")

        compositeDisposable += apiManager.requestGitRepos(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onNext = {
                    if(it.total_count == 0){
                        //empty result
                        Log.d(TAG, "search result is empty")
                        return@subscribeBy
                    }

                    Log.d(TAG, "total count : ${it.total_count}")
                },
                onError = { error -> Log.e(TAG, "requestSearch error : ${error.message}") }
            )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
