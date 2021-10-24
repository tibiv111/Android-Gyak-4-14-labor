package dataclass

import java.time.DateTimeException
import java.time.MonthDay
import java.time.Year
import java.util.Calendar



//data class Date(val year: Year = Year.now(), val month: Month = Month.of(Calendar.MONTH + 1), val day: MonthDay = MonthDay.now())
data class Date(val year : Int = Calendar.YEAR, val month: Int = Calendar.MONTH, val day: Int = Calendar.DAY_OF_MONTH) : Comparable<Date>
{
    override fun toString(): String {
        val yearNew = year.toString().padStart(4, '0')
        val monthNew = month.toString().padStart(2, '0')
        val dayNew = day.toString().padStart(2, '0')

        return "$yearNew/$monthNew/$dayNew"

    }

    override fun compareTo(other: Date): Int {
        val yearcmp = this.year - other.year
        if (yearcmp == 0)
        {
            val monthcmp = this.month - other.month
            if (monthcmp == 0)
            {
                return this.day - other.day

            }
            return monthcmp

        }
        return yearcmp
    }


}
fun Date.isLeapYear(): Boolean {
    var leap = false;
    leap = if (year % 4 == 0) {
        if( year % 100 == 0) {
            year % 400 == 0
        } else {
            true
        }
    } else {
        false
    }
    return leap
}
fun Date.isValid() : Boolean
{
    return try {
        Year.of(year)
        MonthDay.of(month, day)
        true
    }catch (e : DateTimeException) {
        false
    }

}
