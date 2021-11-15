package com.example.quizapp.Retrofit

import com.example.quizapp.models.QuestionModel
import com.example.quizapp.shared.SharedViewModel
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitInterface {

    @get:GET("tibiv111/Android-Kotlin-Gyak-1-4-labor/main/questions.json")
    val posts : Call<List<QuestionModel?>?>?
    companion object
    {
        //"https://raw.githubusercontent.com/tibiv111/Android-Kotlin-Gyak-1-4-labor/main/questions.json"
        const val Base_URL ="https://raw.githubusercontent.com"
    }

}