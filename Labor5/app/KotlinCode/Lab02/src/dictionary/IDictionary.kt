package dictionary

interface IDictionary
{

    companion object //static-et jelent
    {
        var fileName = "dictionary.txt"
    }

    fun add(word: String) : Boolean
    fun find(word: String) : Boolean
    fun size() : Int
}