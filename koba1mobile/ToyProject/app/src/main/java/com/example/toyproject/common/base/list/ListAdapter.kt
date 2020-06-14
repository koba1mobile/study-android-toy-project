package com.example.toyproject.common.base.list

import io.reactivex.subjects.PublishSubject

interface ListAdapter {
    fun getClickSubject(): PublishSubject<ItemData>
}