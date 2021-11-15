package com.example.quizapp.shared


import android.app.Application
import android.nfc.Tag
import android.os.AsyncTask
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quizapp.Retrofit.RetrofitInterface
import com.example.quizapp.models.Question
import kotlinx.coroutines.*
import okhttp3.*
import org.jetbrains.anko.AnkoAsyncContext
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.system.exitProcess

class SharedViewModel(application: Application) : AndroidViewModel(application){

    private var currentQuestionNumber = 0
    val apiLive: MutableLiveData<String> = MutableLiveData()
    private val questions = mutableListOf<Question>()
    private var numberOfCorrectAnswers = 0
    private var playerName: String? = null
    private var highScore: Int = 0
    private var isShuffled = false


    private val context = getApplication<Application>().applicationContext


    fun isShuffled() : Boolean
    {
        return isShuffled
    }




    fun resetQuestions()
    {
        isShuffled = false
        currentQuestionNumber = 0
        numberOfCorrectAnswers = 0
    }
    fun addCorrectAnswer() {numberOfCorrectAnswers++}
    fun getNumberOfCorrectAnswers() = numberOfCorrectAnswers
    fun getResultView() = "$numberOfCorrectAnswers/${getNumberOfQuestions()}"
    fun isLastQuestion() = currentQuestionNumber == getNumberOfQuestions()-1

    fun deleteQuestion(question: Question)
    {

    }

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


    fun addQuestion(question : String, answers : MutableList<String>)
    {
        val newQuestion = Question(question, answers)

        if (newQuestion !in questions)
        {
            questions.add(newQuestion)
        }

    }
    fun randomizeQuestions()
    {
        this.questions.shuffle()
        isShuffled = true;
    }



    /*
//A futashoz be kellett allitsam, hogy a minimum sdk lvl >= 24

    suspend fun getQuestionsRequest() : String
    {
        Log.d("coroutine", "coroutine started")

        return URL("https://raw.githubusercontent.com/tibiv111/Android-Kotlin-Gyak-1-4-labor/main/questions.json").readText()


    }

    fun loadQuestions(apiResponse : String)
    {

        //Log.i("logTest", apiResponse)
        Log.d("coroutine", "before coroutine")

        val questions_json : JSONObject = JSONObject(apiResponse)
        val jsonarray:JSONArray= questions_json.getJSONArray("array")
        val numberOfQuestions = jsonarray.length()
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

    Log.d("coroutine", "after coroutine")

    //Log.i("logTest", numberOfQuestions.toString())
    //Log.i("logTest", jsonarray.toString())



    //val lines = File("res.raw.question_answers.txt").readLines()




    //Log.i("logTest", questions.toString())



 /*
//println("File doesn't exist!")
Log.e("Question_File_Error", "Error with question loading")
        exitProcess(1)
    */

    }





     */


}