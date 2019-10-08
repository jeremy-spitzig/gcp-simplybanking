package com.simplybanking.api.headline

import org.springframework.stereotype.Service

@Service
class HeadlineService (
    val headlineRepository: HeadlineRepository
) {
    fun loadHeadlines() = headlineRepository.findAll().let {
        it.asSequence()
          .sortedByDescending { it.datePosted }
          .toList()
    }
}