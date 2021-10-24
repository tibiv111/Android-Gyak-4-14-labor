package dictionary.types

import dictionary.IDictionary
import java.io.File

object ListDictionary : IDictionary
{

    val words = arrayListOf<String>();
    init{
        if (File(IDictionary.fileName).isFile)
        {
            File(IDictionary.fileName).readLines().forEach{ add(it) }
        }

    }



    override fun add(word: String): Boolean
    {
        if(!find(word))
        {
            words.add(word)
            return true
        }
        return false
    }

    override fun find(word: String): Boolean
    {
        return words.contains(word)
    }

    override fun size(): Int {
        return words.size
    }

}