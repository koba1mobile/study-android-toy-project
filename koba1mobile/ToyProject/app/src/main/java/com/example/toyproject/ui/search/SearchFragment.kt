package com.example.toyproject.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.example.toyproject.R
import com.example.toyproject.api.ApiManager
import com.example.toyproject.api.NetworkState
import com.example.toyproject.common.base.BaseFragment
import com.example.toyproject.controller.SearchController
import com.example.toyproject.model.Repositorys
import io.reactivex.subjects.PublishSubject

class SearchFragment : BaseFragment() {
    private lateinit var apiManager: ApiManager
    private lateinit var searchController: SearchController
    private lateinit var subject: PublishSubject<NetworkState>

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
        subject = PublishSubject.create()
        compositeDisposable += subject.subscribe(this::onSearchResponse)
        searchController = SearchController().apply { searchSubscribe(subject) }
    }

    private fun onSearchResponse(state: NetworkState) {
        when (state) {
            NetworkState.suceess -> {
                
            }
            NetworkState.error -> {

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem: MenuItem = menu.findItem(R.id.search_view)

        // fragment 진입하면 SearchView 바로 활성화
        searchItem.expandActionView()

        with(searchItem.actionView as SearchView) {
            // 검색 요청
            compositeDisposable += searchController.requestSearch(this, apiManager)
        }
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
