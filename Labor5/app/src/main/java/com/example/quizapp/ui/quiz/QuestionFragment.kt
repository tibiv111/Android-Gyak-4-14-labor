package com.example.quizapp.ui.quiz

import android.app.AlertDialog
import android.os.Bundle
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
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI


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

    private var param1: String? = null
    private var param2: String? = null

    private val model : SharedViewModel by activityViewModels()
    private lateinit var questionText: TextView
    private lateinit var answerGroup: RadioGroup
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }



        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true){

            override fun handleOnBackPressed() {
                showExitDialog()
            }
        })

    }

    private fun initializeView(view: View)
    {
        view.apply {
            questionText = view.findViewById(R.id.questionText)
            answerGroup = view.findViewById(R.id.answerGroup)
            nextButton = view.findViewById(R.id.nextButton)
        }


    }
    private fun registerListeners()
    {

    }
    /*

    TODO()
    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.firstAnswer ->
                    if (checked) {
                        // Pirates are the best
                    }
                R.id.secondAnswer ->
                    if (checked) {
                        // Ninjas rule
                    }
                R.id.thirdAnswer ->
                    if (checked) {
                        // Ninjas rule
                    }
                R.id.forthAnswer ->
                    if (checked) {
                        // Ninjas rule
                    }
            }
        }
    }
    */

    private fun showExitDialog()
    {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.apply {
            //setIcon(R.mipmap.brain)
            setTitle("Exit")
            setMessage("Are you sure you want to end this quiz?")
            setPositiveButton("Yes") { _, _ ->
                Toast.makeText(context, "Quiz ended", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.quizEndFragment)
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

        }
        return view
    }


    private fun doQuiz()
    {

        //model.resetQuestions()
        model.randomizeQuestions()
        for(question in model.getQuestions())
        {
            val rightAnswer = question.answers[0]
            question.answers.shuffle()
            for (i in 0 until answerGroup.childCount) {
                (answerGroup.getChildAt(i) as RadioButton).text = question.answers[i]
            }
            TODO()




        }
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
        fun newInstance(param1: String, param2: String) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}