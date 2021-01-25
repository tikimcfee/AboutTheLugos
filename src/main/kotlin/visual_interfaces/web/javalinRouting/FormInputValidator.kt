package visual_interfaces.web.javalinRouting

import io.javalin.http.Context
import org.joda.time.DateTime
import transfomers.DefaultDateParsingSingleton.YearMonthDayFormatting.parseToDate
import visual_interfaces.web.HTMLPageRenderer

typealias NotANumber = NumberFormatException

object FormInputValidator {
    fun Context.formDouble(param: HTMLPageRenderer.FormParam): Double? {
        return try {
            formParam(param.id)?.toDouble()
        } catch (badInput: NotANumber) {
            println("Looked for $param, scored a ${formParam(param.id)}")
            null
        }
    }
    
    fun Context.formInt(param: HTMLPageRenderer.FormParam): Int? {
        return try {
            formParam(param.id)?.toInt()
        } catch (badInput: NotANumber) {
            println("Looked for $param, scored a ${formParam(param.id)}")
            null
        }
    }
    
    fun Context.formString(param: HTMLPageRenderer.FormParam): String =
        formParam(param.id) ?: ""
    
    fun Context.isChecked(param: HTMLPageRenderer.FormParam): Boolean =
        (formParam(param.id) ?: "") == param.id
    
    fun Context.tryFormStringToDate(param: HTMLPageRenderer.FormParam): DateTime? =
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
