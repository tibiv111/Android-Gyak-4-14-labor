package dataclass


import kotlin.random.Random.Default.nextInt

fun main()
{
    val dates = mutableListOf<Date>()
    val start = 0
    val endYear = 9999
    val endMonth = 25
    val endDay = 40
    while (true)
    {
        if(dates.size == 10)
        {
            break
        }
        val year = nextInt(start, endYear)
        val month = nextInt(start, endMonth)
        val day = nextInt(start, endDay)
        val date = Date(year, month, day)
        if(date.isValid())
        {
            dates.add(date)
        }
        else
        {
            println("$date is not a valid date!")
        }
    }
    println("\n---VALID DATES:")
    dates.forEach { println(it) }
    println("\n---SORTED WITH COMPARABLE INTERFACE:")
    dates.sorted().forEach{ println(it)} //comparable
    println("\n---SORTED WITH COMPARATOR:")
    val myCompare = DateComparator()
    dates.sortWith(myCompare) //comparator
    dates.forEach{ println(it)}




}