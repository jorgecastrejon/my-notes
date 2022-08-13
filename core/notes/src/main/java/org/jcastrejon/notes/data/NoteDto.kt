package org.jcastrejon.notes.data

import org.jcastrejon.notes.Note
import orgjcastrejonnotes.Note_dto

fun Note_dto.toDomain(): Note =
    Note(
        id = id,
        title = title,
        body = body,
        createdDate = created_date,
        lastUpdated = last_updated,
        color = color
    )