package dictionary.dictionary

import dictionary.IDictionary
import dictionary.dictionary.types.HashSetDictionary
import dictionary.dictionary.types.TreeSetDictionary
import dictionary.types.ListDictionary

class DictionaryProvider
{
    companion object
    {
        fun createDictionary(type : DictionaryType) : IDictionary
        {
            return when (type)
            {
                DictionaryType.ARRAY_LIST -> ListDictionary
                DictionaryType.TREE_SET -> TreeSetDictionary
                DictionaryType.HASH_SET ->  HashSetDictionary

            }
        }
    }

}