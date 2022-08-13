package org.jcastrejon.notes

import java.time.LocalDate

data class Note(
    val id: Int,
    val title: String,
    val body: String,
    val createdDate: LocalDate,
    val lastUpdated: LocalDate,
    val color: Int
)