package com.simplybanking.api.headline

import org.springframework.data.mongodb.repository.MongoRepository

interface HeadlineRepository : MongoRepository<Headline, String> {
}