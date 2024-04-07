package com.ss.orderfilter

import java.util.Calendar
import java.util.Date

object OrderData {

    val filterOptions = getFilter(generateMonthlyStatements())

    fun getFilter(dateUtils: List<DateUtil>): List<FilterOption> {
        val filterPairs: List<Pair<String, List<DateUtil>>> =
            listOf(Pair("Last 12 Months", getLast12MonthsStatements(dateUtils))) + getYearToStatementsMap(dateUtils)

        return filterPairs.mapIndexed { index, (option, statementList) ->
            FilterOption(option, statementList, index == 0)
        }
    }
    fun printList(filterList: List<FilterOption>){
        filterList.filter { it.selected }
        filterList.forEach{
            println("option ${it.option}   " + "enable: ${it.selected}" )
        }
    }

//    fun getStatementsForYear(year: String, statements: List<Statement>): List<Statement>? {
//        return getYearToStatementsMap(statements).find { it.first == year }?.second
//    }

    private fun getYearToStatementsMap(dateUtils: List<DateUtil>): List<Pair<String, List<DateUtil>>> {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        val uniqueYears = dateUtils.filter { it.date.year + 1900 != currentYear }.map { it.date.year + 1900 }.distinct().sortedDescending()

        return uniqueYears.map { year ->
            year.toString() to dateUtils.filter { it.date.year + 1900 == year }
                .sortedByDescending { it.date }
        }
    }

    // Function to get the last 12 months statements
    private fun getLast12MonthsStatements(dateUtils: List<DateUtil>): List<DateUtil> {
        val currentDate = Date()
        val last12MonthsStart = Calendar.getInstance().apply { add(Calendar.MONTH, -12) }.time
        return dateUtils.filter { it.date >= last12MonthsStart && it.date <= currentDate }.sortedByDescending { it.date }
    }

    fun generateMonthlyStatements(): List<DateUtil> {
        val dateUtils = mutableListOf<DateUtil>()
        val currentDate = Date()
        val calendar = Calendar.getInstance()

        // Generate statements for each month in the last 10 years
        for (year in (currentDate.year - 10 + 1900)..(currentDate.year + 1900)) {
            for (month in 0 until 12) {
                calendar.set(year, month, 1)
                val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

                // Generate statements for the 1st and 15th day of each month
                for (day in listOf(1, 15)) {
                    if (day <= daysInMonth) {
                        val statementDate = Date(year - 1900, month, day)
                        val id = "Statement_${dateUtils.size + 1}"
                        dateUtils.add(DateUtil(id, statementDate,"Chrome & Coral","Printed Shirt Collar with Trouser Co-Ords","MRP. 2000 INR"))
                    }
                }
            }
        }

        println("Generated semi-monthly statements for the last 10 years:")
        dateUtils.forEach { println("Statement ID: ${it.id}, Date: ${it.date}") }

        return dateUtils
    }

}