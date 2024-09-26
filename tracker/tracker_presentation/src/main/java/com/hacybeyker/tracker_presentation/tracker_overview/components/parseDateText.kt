package com.hacybeyker.tracker_presentation.tracker_overview.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun parseDateText(date: LocalDate): String {
    val today = LocalDate.now()
    return when (date) {
        today -> stringResource(id = com.hacybeyker.core.R.string.today)
        today.minusDays(1) -> stringResource(id = com.hacybeyker.core.R.string.yesterday)
        today.plusDays(1) -> stringResource(id = com.hacybeyker.core.R.string.tomorrow)
        else -> DateTimeFormatter.ofPattern("dd LLLL").format(date)
    }
}
