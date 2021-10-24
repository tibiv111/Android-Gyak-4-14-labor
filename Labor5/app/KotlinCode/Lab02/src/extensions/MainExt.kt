package dictionary.extensions

fun main()
{
    val name = "Vancza Tibor"

    println("---Elso feladat: \n" + name.monogramOfName())
    val myList = listOf("Elso", "Masodik", "Harmadik", "Tizenkilencedik")
    println("---Masodik feladat: \n" + myList.stringJoin())
    println("---Harmadik feladat: \n" +myList.longestString())


}

//extension feladatok ide.

fun String.monogramOfName() = this.split(" ").map { it.first().toUpperCase() }.joinToString("")

fun List<String>.stringJoin() = this.joinToString("#")

fun List<String>.longestString() = this.maxByOrNull { it.length }

                                    //ha a hossza kell akkor this.maxOf{ it.length }

