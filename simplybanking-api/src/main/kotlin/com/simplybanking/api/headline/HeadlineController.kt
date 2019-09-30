package com.simplybanking.api.headline

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/headline")
class HeadlineController (
    val headlineService: HeadlineService
) {
    @GetMapping(value=["/", ""])
    fun listHeadlines() : List<Headline> = headlineService.loadHeadlines()
}