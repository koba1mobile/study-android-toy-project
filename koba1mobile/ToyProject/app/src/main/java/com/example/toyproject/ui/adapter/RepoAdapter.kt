package com.example.toyproject.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toyproject.R
import com.example.toyproject.common.base.list.ItemData
import com.example.toyproject.common.base.list.ListAdapter
import com.example.toyproject.common.base.list.ViewHolder
import com.example.toyproject.model.Repositorys
import com.example.toyproject.model.domain.GitItem
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.holder_git_repo.view.*

class RepoAdapter(private val context: Context?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    ListAdapter {

    private lateinit var eventSubject: PublishSubject<ItemData>
    private lateinit var repos: List<GitItem>

    fun setClickEventSubject(subject: PublishSubject<ItemData>) = apply { eventSubject = subject }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GitRepoViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.holder_git_repo,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(repos[position])
        }
    }

    fun setData(list: List<GitItem>) = apply { repos = list }

    override fun getClickSubject(): PublishSubject<ItemData> = eventSubject

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        getClickSubject().onComplete()
    }

    inner class GitRepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ViewHolder {
        init {
            itemView.clicks()
                .map { repos[layoutPosition] }
                .subscribe(getClickSubject())
        }

        override fun bind(itemData: ItemData) {
            val item = itemData as GitItem
            with(itemView) {
                com.bumptech.glide.Glide.with(context)
                    .load(item.owner.thumbnail_url)
                    .into(iv_avatar)

                tv_full_name.text = item.full_name
                tv_language.text = item.language ?: ""
            }
        }

    }
}