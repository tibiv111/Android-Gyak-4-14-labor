package com.example.quizapp.models

data class Question(val question : String, val answers : MutableList<String>, val questionType : String = "Single answer")
{


    override fun toString(): String {
        return "$question\n" +
                "a.) ${answers[0]}\n" +
                "b.) ${answers[1]}\n" +
                "c.) ${answers[2]}\n" +
                "d.) ${answers[3]}\n"

    }

    override fun equals(other: Any?): Boolean {
        if ((other as Question).question == this.question)
        {
            return this.answers.zip((other as Question).answers).all { (x, y) -> x == y }
        }
        return false
    }


}
