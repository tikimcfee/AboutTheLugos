package transfomers

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatterBuilder

object DefaultDateParsingSingleton {
    
    private val yearMonthDayBuilder =
        DateTimeFormatterBuilder()
            .appendYear(4, 4)
            .appendLiteral('-')
            .appendMonthOfYear(2)
            .appendLiteral('-')
            .appendDayOfMonth(2)
    
    private val longMonthLongYearBuilder =
        DateTimeFormatterBuilder()
            .appendMonthOfYearText()
            .appendLiteral(", ")
            .appendYear(4, 4)
    
    private val yearMonthDayFormatter = yearMonthDayBuilder.toFormatter()
    private val longMonthLongYearFormatter = longMonthLongYearBuilder.toFormatter()
    
    object Simple {
        private fun DateTime.format() = yearMonthDayFormatter.print(this)
    
    }
    
    object YearMonthDayFormatting {
        fun String.parseToDate(): DateTime = try {
            yearMonthDayFormatter.parseDateTime(this)
        } catch (e: Exception) {
            println("Parse failure [$yearMonthDayBuilder]<[$this]")
            println(e)
            throw e
        }
        
        fun DateTime.format(): String = yearMonthDayFormatter.print(this)
    
        fun DateTime.longMonthLongYear(): String = longMonthLongYearFormatter.print(this)
    }
}
