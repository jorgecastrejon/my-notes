package org.jcastrejon.features.detail.ui

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.toReadableFormat(showYear: Boolean = false): String =
    format(DateTimeFormatter.ofPattern("MMM d${if (showYear) ", yyyy" else ""}"))
        .replaceFirstChar(Char::titlecase)