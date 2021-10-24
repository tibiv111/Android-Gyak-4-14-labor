data class Question(val question : String, val answers : MutableList<String>)
{

    override fun toString(): String {
        return "$question\n" +
                "a.) ${answers[0]}\n" +
                "b.) ${answers[1]}\n" +
                "c.) ${answers[2]}\n" +
                "d.) ${answers[3]}\n" +
                "YOUR ANSWER(a/b/c/d):  "
    }
}
