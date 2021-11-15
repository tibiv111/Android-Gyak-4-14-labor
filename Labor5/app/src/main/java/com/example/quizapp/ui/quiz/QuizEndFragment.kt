package com.example.quizapp.ui.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapp.MainActivity
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuestionBinding
import com.example.quizapp.databinding.FragmentQuizEndBinding
import com.example.quizapp.shared.SharedViewModel
import android.widget.Button as Button

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizEndFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizEndFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val model : SharedViewModel by activityViewModels()
    private lateinit var quizResult : TextView
    private lateinit var tryAgain_btn : Button
    //private val binding = FragmentQuizEndBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true){

            override fun handleOnBackPressed() {

                findNavController().navigate(R.id.action_quizEndFragment_to_homeFragment2)

            }
        })
    }

    override fun onStop() {
        super.onStop()
        model.resetQuestions()
    }
    private fun initializeView(view: View)
    {
        val points : String = model.getNumberOfCorrectAnswers().toString() + "/" + model.getNumberOfQuestions() + " points"
        (activity as MainActivity).setDrawerLocked(false)
        view.apply {

            quizResult = view.findViewById(R.id.quizResult)
            quizResult.text = points
            tryAgain_btn = view.findViewById(R.id.try_again_btn)



            /*
            quizResult = binding.quizResult
            quizResult.text = points
            tryAgain_btn = binding.tryAgainBtn

             */
        }
    }

    private fun registerListeners()
    {
        tryAgain_btn.setOnClickListener{
            findNavController().navigate(R.id.action_quizEndFragment_to_homeFragment2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz_end, container, false)
        if (view != null)
        {
            initializeView(view)
            registerListeners()

        }

        model.updatedHighScore()
        // Inflate the layout for this fragment
        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuizEndFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuizEndFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}