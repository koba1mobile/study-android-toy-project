package com.example.toyproject.model

object Repositorys {

    val gitItemRepository: GitItemRepository by lazy { createGitItemRepository() }

    private fun createGitItemRepository(): GitItemRepository = GitItemRepository()
}