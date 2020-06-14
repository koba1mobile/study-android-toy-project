package com.example.toyproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.toyproject.R
import com.example.toyproject.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_history.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HistoryFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        fab.setOnClickListener { findNavController().navigate(R.id.action_HistoryFragment_to_SearchFragment) }
    }
}
