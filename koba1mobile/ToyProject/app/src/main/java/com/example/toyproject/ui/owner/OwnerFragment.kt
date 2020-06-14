package com.example.toyproject.ui.owner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.toyproject.R
import com.example.toyproject.common.base.BaseFragment
import com.example.toyproject.model.domain.GitItem
import com.example.toyproject.ui.SharedViewModel
import kotlinx.android.synthetic.main.fragment_owner.*

class OwnerFragment : BaseFragment() {
    // view model
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_owner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        // toolbar에 back버튼 생성
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // onCreateOptionsMenu 실행되도록
        setHasOptionsMenu(true)

        sharedViewModel.itemLiveData.observe(viewLifecycleOwner,
            Observer<GitItem>{ item ->
                tv_full_name.text = item.full_name
                tv_description.text = item.description
                tv_star_num.text = item.stargazers_count.toString()
                tv_language.text = item.language
                tv_update.text = item.updated_at

                context?.let{
                    Glide.with(it)
                        .load(item.owner.thumbnail_url)
                        .into(iv_avatar)
                }
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                findNavController().navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}