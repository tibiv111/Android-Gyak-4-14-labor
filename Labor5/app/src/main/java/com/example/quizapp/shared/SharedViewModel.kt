package com.example.quizapp.shared


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.quizapp.models.Question
import java.io.File
import kotlin.system.exitProcess

class SharedViewModel(application: Application) : AndroidViewModel(application){

    private var currentQuestionNumber = 0;
    private val questions = mutableListOf<Question>()
    private var numberOfCorrectAnswers = 0;
    private var playerName: String? = null
    private var highScore: Int = 0

    private val context = getApplication<Application>().applicationContext

    init {
        loadQuestions()
    }

    fun resetQuestions()
    {
        currentQuestionNumber = 0
        numberOfCorrectAnswers = 0
    }
    fun addCorrectAnswer() = {numberOfCorrectAnswers++}
    fun getNumberOfCorrectAnswers() = numberOfCorrectAnswers
    fun getResultView() = "$numberOfCorrectAnswers/${getNumberOfQuestions()}"





    fun randomizeQuestions() = this.questions.shuffle()
    private fun loadQuestions()
    {


        if (File("res.raw.question_answers.txt").isFile)
        {
            val lines = File("res.raw.question_answers.txt").readLines()


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

    fun isLastQuestion() = currentQuestionNumber == getNumberOfQuestions()-1

    fun getHighScore() = highScore

    fun updatedHighScore() : Boolean {
        if(numberOfCorrectAnswers > highScore)
        {
            highScore = numberOfCorrectAnswers
            return true
        }
        return false
    }
    fun getQuestions() = questions
    fun getPlayerName() = playerName
    fun changePlayerName(playerName: String) {this.playerName = playerName}
    fun getSpecificQuestion(index : Int) = questions[index]
    fun getNumberOfQuestions() = questions.size
    fun incrementCurrentQuestionNumber()
    {
        if(currentQuestionNumber < questions.size-1)
        {
            currentQuestionNumber++
        }
    }

    fun getCurrentCorrectAnswer() = questions[currentQuestionNumber].answers[0]
    fun getCurrentQuestionNumber() = currentQuestionNumber




}