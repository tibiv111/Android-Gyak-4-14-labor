package com.example.quizapp.ui.questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.shared.SharedViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuestionAddragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestionAddragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var questionText: TextView
    private lateinit var answer1 : TextView
    private lateinit var answer2 : TextView
    private lateinit var answer3 : TextView
    private lateinit var answer4 : TextView
    private lateinit var addQuestionbtn : Button
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_question_addragment, container, false)
        //BackButton()
        if (view != null) {
            initializeView(view)
            registerListeners()
        }


        return view
    }


    fun initializeView(view : View)
    {
        view.apply {
            questionText = view.findViewById(R.id.question_add_textEdit)
            answer1 = view.findViewById(R.id.answer_add_textEdit)
            answer2 = view.findViewById(R.id.answer2_add_textEdit2)
            answer3 = view.findViewById(R.id.answer3_add_textEdit2)
            answer4 = view.findViewById(R.id.answer4_add_textEdit2)
            addQuestionbtn = view.findViewById(R.id.add_question_btn)
        }
    }


    fun registerListeners()
    {
        addQuestionbtn.setOnClickListener {
            if(questionText == null || answer1 == null || answer2 == null || answer3 == null || answer4 == null)
            {
                Toast.makeText(context, "Please fill the data", Toast.LENGTH_SHORT).show()
            }
            else
            {
                viewModel.addQuestion(questionText.text.toString(), mutableListOf(answer1.text.toString(),
                                                                                answer2.text.toString(),
                                                                                answer3.text.toString(),
                                                                                answer4.text.toString()))
                Toast.makeText(context, "Question added!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_questionAddragment_to_homeFragment)
            }
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuestionAddragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuestionAddragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}