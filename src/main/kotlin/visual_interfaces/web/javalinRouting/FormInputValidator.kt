package visual_interfaces.web.javalinRouting

import io.javalin.http.Context
import org.joda.time.DateTime
import transfomers.DefaultDateParsingSingleton.YearMonthDayFormatting.parseToDate
import visual_interfaces.web.htmlPages.FormParam

typealias NotANumber = NumberFormatException

object FormInputValidator {
    fun Context.formDouble(param: FormParam): Double? {
        return try {
            formParam(param.id)?.toDouble()
        } catch (badInput: NotANumber) {
            println("Looked for $param, scored a ${formParam(param.id)}")
            null
        }
    }
    
    fun Context.formInt(param: FormParam): Int? {
        return try {
            formParam(param.id)?.toInt()
        } catch (badInput: NotANumber) {
            println("Looked for $param, scored a ${formParam(param.id)}")
            null
        }
    }
    
    fun Context.formString(param: FormParam): String =
        formParam(param.id) ?: ""
    
    fun Context.isChecked(param: FormParam): Boolean =
        (formParam(param.id) ?: "") == param.id
    
    fun Context.tryFormStringToDate(param: FormParam): DateTime? =
        with(formString(param)) {
            (parseAsFormattedDate() ?: parseAsEpochLong()).also {
                if (it == null) {
                    println("Tried to parse a string into a Date. No such luck: ${this@with}")
                }
            }
        }
    
    fun String.parseAsFormattedDate() = try {
        parseToDate()
    } catch (badInput: Exception) {
        null
    }
    
    fun String.parseAsEpochLong() = try {
        DateTime(toLong())
    } catch (badInput: Exception) {
        null
    }
}
