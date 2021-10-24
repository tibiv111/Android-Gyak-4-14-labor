import java.io.File
import kotlin.system.exitProcess

class QuizController
{
    private val questions = mutableListOf<Question>()
    init{
        if (File("question-answers.txt").isFile)
        {
            val lines = File("question-answers.txt").readLines()


            for (i in 0..lines.size-1 step 5)
            {

                val question = Question(lines[i], mutableListOf(lines[i+1], lines[i+2], lines[i+3], lines[i+4]))
                questions.add(question)

            }
        }
        else
        {
            println("File doesn't exist!")
            exitProcess(1)
        }
    }
    private fun randomizeQuestions() = this.questions.shuffle()

    fun doQuiz(nrOfQuestions : Int) {
        randomizeQuestions()

        if (nrOfQuestions <= questions.size && nrOfQuestions > 0)
        {
            val selectedQuestions = questions.subList(0, nrOfQuestions)
            val rightAnswers = mutableListOf<Boolean>()
            for (question in selectedQuestions)
            {
                val rightAnswer = question.answers[0]
                question.answers.shuffle()
                println(question)
                val input = readLine()

                when(input)
                {
                    "a" -> { if(question.answers[0] == rightAnswer) {rightAnswers.add(true)} else{rightAnswers.add(false)}}
                    "b" -> { if(question.answers[1] == rightAnswer) {rightAnswers.add(true)} else{rightAnswers.add(false)}}
                    "c" -> { if(question.answers[2] == rightAnswer) {rightAnswers.add(true)} else{rightAnswers.add(false)}}
                    "d" -> { if(question.answers[3] == rightAnswer) {rightAnswers.add(true)} else{rightAnswers.add(false)}}
                    else -> { rightAnswers.add(false) }
                }
            }
            val nrOfRightAnswers = rightAnswers.filter { it }.size

            println("\nThe number of right answers: $nrOfRightAnswers\nTotal number of answers: ${rightAnswers.size}")
        }
        else
        {
            throw Exception("\nWrong number of questions!\n")
        }


    }

}
