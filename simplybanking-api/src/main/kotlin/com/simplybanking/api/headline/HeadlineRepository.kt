package com.simplybanking.api.headline

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository

interface HeadlineRepository : DatastoreRepository<Headline, String> {
}