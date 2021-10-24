package fundamentals


import java.util.*
import kotlin.random.Random.Default.nextInt
import java.util.Random

fun main()
{

    println("\n-----1. Feladat------\n")
    fel1()

    println("\n-----2. Feladat------\n")

    val daysOfWeek : List<String> = listOf("monday", "Tuesday", "wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    for(item in daysOfWeek)
    {
        println(item)
    }

    fel2(daysOfWeek)
    //daysOfWeek.forEach {day -> println(day)}

    //daysOfWeek.forEach {println(it)}



    println("\n-----3. Feladat------\n")
    println("Prime numbers: ")
    for (i in 0..100)
    {

        val k = fel3(i);
        if (k)
        {
            print( "$i ")
        }


    }
    println("\n-----4. Feladat------\n")
    val encodedStringMain = messageCoding("Hi there. How are you?", ::encode)
    messageCoding(encodedStringMain, ::decode)

    //encode("Hi there. How are you?");
    //decode("SGkgdGhlcmUuIEhvdyBhcmUgeW91Pw==");
    println("\n-----5. Feladat------\n")

    val sourceList = mutableListOf<Int>()
    for (i in 0..100)
    {
        sourceList.add(i);
    }
    sourceList.filter { even(it) }.forEach{print("$it ")}

    println("\n-----6. Feladat------")

    println("\n--Double of list")
    sourceList.map { double(it) }.forEach{print("$it ")}
    println("\n--Uppercase")
    daysOfWeek.map { it.uppercase() }.forEach { print("$it ") }
    println("\n--Capitalize")
    daysOfWeek.map { it.capitalize()}.forEach { print("$it ") }
    println("\n--Length of days")
    daysOfWeek.map { it.length }.forEach { print("$it ") }
    println("\n--Average length of days")
    println(daysOfWeek.map { it.length }.average())

    println("\n-----7. Feladat------\n")

    print(daysOfWeek.toMutableList().filter { !it.contains("n") })
    println("\n")
    daysOfWeek.toMutableList().filter { !it.contains("n") }.forEachIndexed{ index, it -> println("Item at $index is $it")}
    println()
    print(daysOfWeek.toMutableList().filter { !it.contains("n") }.sorted())


    println("\n-----8. Feladat------\n")

    println("--Numbers")
    var numbers = (1..10).map { nextInt(0, 100) } //irhattam volna egy sorba, de nem akartam
    numbers.forEach{ print("$it\n")}
    println("--Sorted")
    println(numbers.sorted())
    println("--Doesn't have even numbers?")
    println(numbers.filter { even(it) }.isEmpty())
    println("--All numbers are even?")
    println(numbers.filter { even(it) }.size == numbers.size)
    println("--Average of numbers")
    //ezt a feladatot nem értem. A foreach az listák iterálására való. Egyelemű listát kiíratni felesleges foreach-el

    println(numbers.average())





}

fun fel1()
{
    val num1 = 2
    val num2 = 3

    println( "$num1 + $num2 = ${num1+num2}")

}

fun fel2(daysOfWeek:List<String>)
{
    println("\n--Starts with 'T'")
    daysOfWeek.filter{it.startsWith('T')}.forEach{println(it)}
    println("\n--Contains 'e'")
    daysOfWeek.filter{it.contains('e')}.forEach{println(it)}
    println("\n--weekday length = 6")
    daysOfWeek.filter { it.length == 6  }.forEach{println(it)}
}

fun fel3(n : Int) : Boolean
{
    if (n == 0 || n == 1)
    {
        return false
    }
    var i = 2
    var p = 1
    while (i < n) {
        if (n % i == 0) {
            p = 0
            break
        }
        i++
    }

    return p == 1
}




fun messageCoding(msg: String, func: (String) -> String): String
{
    return func(msg)
}


fun encode(oriString:String) : String
{
    val encodedString: String = Base64.getEncoder().encodeToString(oriString.toByteArray())
    println(encodedString)
    return encodedString

}

fun decode(encodedString:String) : String
{
    val decodedBytes = Base64.getDecoder().decode(encodedString)
    val decodedString = String(decodedBytes)
    println(decodedString)
    return decodedString

}
fun even(x: Int):Boolean = (x % 2 == 1)
fun double(x: Int):Int = x * 2



