package com.simplybanking.api.global

class EntityNotFoundException(
    entityType : String,
    id : String
) : Exception("Entity of type ${entityType} with ID \"${id}\" not found.")