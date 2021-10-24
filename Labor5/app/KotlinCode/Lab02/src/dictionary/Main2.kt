package dictionary
import dictionary.dictionary.DictionaryProvider
import dictionary.dictionary.DictionaryType
import dictionary.types.ListDictionary



fun main(){
    //val dict: IDictionary = ListDictionary
    //val dict: IDictionary = DictionaryProvider.createDictionary(DictionaryType.TREE_SET)
    val dict: IDictionary = DictionaryProvider.createDictionary(DictionaryType.HASH_SET)
    println("Number of words: ${dict.size()}")
    var word: String?
    while(true){
        print("What to find? ")
        word = readLine()
        if( word.equals("quit")){
            break
        }
        println("Result: ${word?.let { dict.find(it) }}")
    }
}

//problem 2
/*

 */