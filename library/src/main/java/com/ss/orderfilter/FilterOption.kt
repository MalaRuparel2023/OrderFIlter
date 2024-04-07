package com.ss.orderfilter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class FilterOption(val option: String, val dateUtil: List<DateUtil>, initialSelection: Boolean = false) {
    var selected by mutableStateOf(initialSelection)
}
