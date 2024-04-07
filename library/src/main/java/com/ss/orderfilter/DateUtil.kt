package com.ss.orderfilter

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

data class DateUtil(val id: String, val date: Date, val name :String , val description:String,val price :String){

    private val format: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
    private val localDate: LocalDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val displayDate: String = localDate.format(format)
}
