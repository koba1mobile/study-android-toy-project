package com.example.toyproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toyproject.R
import com.example.toyproject.common.base.BaseFragment
import com.example.toyproject.controller.HistoryController
import com.example.toyproject.model.DataState
import com.example.toyproject.model.Repositorys
import com.example.toyproject.ui.adapter.RepoAdapter
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HistoryFragment : BaseFragment() {
    // view
    private lateinit var repoAdpter: RepoAdapter

    // evnet
    private lateinit var dataSubject: PublishSubject<DataState>

    // controller
    private lateinit var historyController: HistoryController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initView()
    }

    private fun init() {
        repoAdpter = RepoAdapter(context)
        dataSubject = PublishSubject.create()
        compositeDisposable += dataSubject.subscribe(this::onFetchHistory)

        context?.let { historyController = HistoryController(it) }
    }

    private fun initView() {
        // toolbar에 back버튼 생성
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        // onCreateOptionsMenu 실행되도록
        setHasOptionsMenu(true)

        fab.setOnClickListener { findNavController().navigate(R.id.action_HistoryFragment_to_SearchFragment) }

        recycler_view.apply {
            adapter = repoAdpter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onResume() {
        super.onResume()

        // data reflesh
        context?.let { compositeDisposable += historyController.fetchHistoryItems(dataSubject) }
    }

    private fun onFetchHistory(dataState: DataState) {
        when (dataState) {
            DataState.suceess -> {
                repoAdpter.setData(Repositorys.gitItemRepository.historyItems)
                repoAdpter.notifyDataSetChanged()
            }
            else -> {
            }
        }

    }
}
