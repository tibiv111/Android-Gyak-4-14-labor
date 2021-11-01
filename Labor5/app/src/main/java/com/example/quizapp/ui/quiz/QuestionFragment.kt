package com.example.quizapp.ui.quiz

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.shared.SharedViewModel
import android.widget.RadioButton
//import com.example.quizapp.databinding.FragmentQuestionBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestionFragment : Fragment() {

    private var isFirst: Boolean? = null
    private var param2: String? = null

    private val model : SharedViewModel by activityViewModels()
    private lateinit var correctAnswer : String
    private lateinit var questionText: TextView
    private lateinit var answerGroup: RadioGroup
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("fragmentSELF", "onCreate happening")
        arguments?.let {
            if(it.getBoolean(ARG_PARAM1) == null)
            {
                isFirst = true
                Log.d("fragmentSELF", "isFirst set to True on onCreate")
            }
            param2 = it.getString(ARG_PARAM2)
        }




        //val binding = FragmentQuestionBinding.inflate(layoutInflater)


        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true){

            override fun handleOnBackPressed() {
                showExitDialog()
            }
        })

        Log.d("fragmentSELF", "onCreate happened")

    }

    private fun startQuiz()
    {
        if(isFirst == true)
        {
            Log.d("fragmentSELF", "isFirst happened")
            model.randomizeQuestions()
            isFirst = false
        }
        changeQuestion()
    }
    private fun initializeView(view: View)
    {
        view.apply {


            questionText = view.findViewById(R.id.questionText)
            answerGroup = view.findViewById(R.id.answerGroup)
            nextButton = view.findViewById(R.id.nextButton)
            Log.d("fragmentSELF", "initializeView happened")
            if (model.isLastQuestion())
            {
                nextButton.text = context.resources.getString(R.string.submit)
            }
        }


    }
    private fun registerListeners()
    {


        Log.d("fragmentSELF", "registerListeners happening")
        nextButton.setOnClickListener{

            val selectedOption: Int = answerGroup.checkedRadioButtonId
            Log.d("logTest", selectedOption.toString())
            if (selectedOption == -1)
            {
                Toast.makeText(this.context, "Choose an answer", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val radiobutton : RadioButton = view!!.findViewById<RadioButton>(selectedOption)
                //Log.d("fragmentSELF", radiobutton.text.toString())
                if (correctAnswer == radiobutton.text.toString())
                {
                    model.addCorrectAnswer()
                }
                if(model.isLastQuestion())
                {
                    findNavController().navigate(R.id.action_questionFragment_to_quizEndFragment)

                }
                else
                {
                    model.incrementCurrentQuestionNumber()
                    answerGroup.clearCheck()
                    findNavController().navigate(R.id.action_questionFragment_self)
                }
            }

        }
        Log.d("fragmentSELF", "registerListeners happened")
    }







    private fun showExitDialog()
    {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.apply {

            setTitle("Exit")
            setMessage("Are you sure you want to end this quiz?")
            setPositiveButton("Yes") { _, _ ->
                Toast.makeText(context, "Quiz ended", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_questionFragment_to_quizEndFragment)
            }
            setNegativeButton("No") { _, _ ->
                Toast.makeText(context, "You can continue the quiz",Toast.LENGTH_SHORT).show()
            }
        }
        alertDialog.create().show()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        //BackButton()TODO()
        if (view != null)
        {
            initializeView(view)
            registerListeners()

        }
        Log.d("fragmentSELF", "onCreateView happened")
        startQuiz()
        return view

    }



    private fun changeQuestion()
    {

        //model.resetQuestions()
        Log.d("fragmentSELF", "changeQuestion happening")
        questionText.text = model.getQuestions()[model.getCurrentQuestionNumber()].question //kerdes
        correctAnswer = model.getCurrentCorrectAnswer()//jo valasz
        model.getQuestions()[model.getCurrentQuestionNumber()].answers.shuffle() //valaszok keverese

        for (i in 0 until answerGroup.childCount) {
            (answerGroup.getChildAt(i) as RadioButton).text = model.getQuestions()[model.getCurrentQuestionNumber()].answers[i]
        }
        Log.d("fragmentSELF", "changeQuestion happened")









    }

    /*
    fun doQuiz() {


        randomizeQuestions()

        if (nrOfQuestions <= questions.size && nrOfQuestions > 0)
        {
            val selectedQuestions = questions.subList(0, nrOfQuestions)
            val rightAnswers = mutableListOf<Boolean>()
            for (question in selectedQuestions)
            {
                val rightAnswer = getCurrentCorrectAnswer()
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

            //println("\nThe number of right answers: $nrOfRightAnswers\nTotal number of answers: ${rightAnswers.size}")
        }
        else
        {
            throw Exception("\nWrong number of questions!\n")
        }


    }
    */

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuestionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(isFirst: Boolean, param2: String) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_PARAM1, isFirst)
                    putString(ARG_PARAM2, param2)
                    Log.d("fragmentSELF", "newInstance comp obj happened")
                }
            }
    }

}