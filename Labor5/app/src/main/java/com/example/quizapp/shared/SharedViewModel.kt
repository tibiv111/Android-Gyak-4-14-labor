package com.example.quizapp.shared


import android.app.Application
import android.os.AsyncTask
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import com.example.quizapp.models.Question
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.*
import org.jetbrains.anko.AnkoAsyncContext
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.system.exitProcess

class SharedViewModel(application: Application) : AndroidViewModel(application){

    private var currentQuestionNumber = 0;
    private val questions = mutableListOf<Question>()
    private var numberOfCorrectAnswers = 0;
    private var playerName: String? = null
    private var highScore: Int = 0







    private val context = getApplication<Application>().applicationContext




    fun resetQuestions()
    {
        currentQuestionNumber = 0
        numberOfCorrectAnswers = 0
    }
    fun addCorrectAnswer() {numberOfCorrectAnswers++}
    fun getNumberOfCorrectAnswers() = numberOfCorrectAnswers
    fun getResultView() = "$numberOfCorrectAnswers/${getNumberOfQuestions()}"
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
        if(currentQuestionNumber < questions.size)
        {
            currentQuestionNumber++
        }
    }

    fun getCurrentCorrectAnswer() = questions[currentQuestionNumber].answers[0]
    fun getCurrentQuestionNumber() = currentQuestionNumber


    fun randomizeQuestions() = this.questions.shuffle()



//A futashoz be kellett allitsam, hogy a minimum sdk lvl >= 24

    internal fun loadQuestions()
    {
        val apiResponse = URL("https://raw.githubusercontent.com/tibiv111/Android-Kotlin-Gyak-1-4-labor/main/questions.json").readText()
        //Log.i("logTest", apiResponse)

        val questions_json : JSONObject = JSONObject(apiResponse)
        val jsonarray:JSONArray= questions_json.getJSONArray("array")
        val numberOfQuestions = jsonarray.length()
        //Log.i("logTest", numberOfQuestions.toString())
        //Log.i("logTest", jsonarray.toString())



        //val lines = File("res.raw.question_answers.txt").readLines()


        for (i in 0 until numberOfQuestions)
        {
            val item = jsonarray.getJSONObject(i)
            //Log.i("logTest", item.toString())
            val answerArray = item.getJSONArray("answers")
            //Log.i("logTest", answerArray.toString())
            val answers = mutableListOf<String>()
            //itt meg van oldva hogy valtozo valaszlehetoseg legyen
            for(i in 0 until answerArray.length())
            {
                answers.add(answerArray.getString(i))
            }
            val question = Question(item.getString("question"), answers)
            questions.add(question)

        }
        Log.i("logTest", "Questions have been loaded!")

        //Log.i("logTest", questions.toString())



     /*
    //println("File doesn't exist!")
    Log.e("Question_File_Error", "Error with question loading")
            exitProcess(1)
        */

    }






}