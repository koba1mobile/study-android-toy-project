package com.example.toyproject.ui.search

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toyproject.R
import com.example.toyproject.api.ApiManager
import com.example.toyproject.api.NetworkState
import com.example.toyproject.common.base.BaseFragment
import com.example.toyproject.common.base.list.ItemData
import com.example.toyproject.controller.SearchController
import com.example.toyproject.model.domain.GitItem
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment() {
    // view
    private lateinit var searchAdapter: SearchAdapter

    // controller
    private var apiManager = ApiManager()
    private lateinit var searchController: SearchController

    // event
    private lateinit var searchSubject: PublishSubject<NetworkState>
    private lateinit var clickSubject: PublishSubject<ItemData>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initView()
    }

    private fun init() {
        searchSubject = PublishSubject.create()
        clickSubject = PublishSubject.create()
        compositeDisposable += searchSubject.subscribe(this::onSearchResponse)
        compositeDisposable += clickSubject.subscribe(this::onClick)
        searchController = SearchController(context).apply { searchSubscribe(searchSubject) }
        searchAdapter = SearchAdapter(context).setClickEventSubject(clickSubject)
    }

    private fun initView() {
        // toolbar에 back버튼 생성
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // onCreateOptionsMenu 실행되도록
        setHasOptionsMenu(true)

        recycler_view.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun onSearchResponse(state: NetworkState) {
        when (state) {
            NetworkState.suceess -> {
                searchAdapter.notifyDataSetChanged()
            }
            NetworkState.error ->{
            }
        }
    }

    private fun onClick(itemData: ItemData){
        val item = itemData as GitItem
        Log.d(TAG, item.full_name)
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
