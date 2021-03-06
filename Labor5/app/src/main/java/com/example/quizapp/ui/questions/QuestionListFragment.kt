package com.example.quizapp.ui.questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.models.Question
import com.example.quizapp.models.questionAdapter
import com.example.quizapp.shared.SharedViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuestionListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestionListFragment : Fragment(),questionAdapter.onItemClickListener {

    private var param1: String? = null
    private var param2: String? = null
    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var questionList: MutableList<Question>
    //private val questionList = viewModel.getQuestions()
    //private val adapter = questionAdapter(questionList, this)
    private lateinit var deleteBtn : Button
    private lateinit var recycler_view : RecyclerView

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


        val view = inflater.inflate(R.layout.fragment_question_list, container, false)
        //BackButton()
        if (view != null) {
            initializeView(view)
            //registerListeners()
        }


        return view



    }

    fun initializeView(view :View)
    {
        questionList = viewModel.getQuestions()
        view.apply {

            recycler_view = view.findViewById(R.id.recycler_view)
            recycler_view.adapter = questionAdapter(questionList, this@QuestionListFragment)
            recycler_view.layoutManager = LinearLayoutManager(view.context)
            recycler_view.setHasFixedSize(true)

        }


    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this.context, "Item $position deleted", Toast.LENGTH_SHORT).show()
        val clickedItem = viewModel.getSpecificQuestion(position)
        viewModel.deleteQuestion(clickedItem)
        questionList.removeAt(position)
        recycler_view.adapter!!.notifyItemChanged(position)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuestionListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuestionListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}