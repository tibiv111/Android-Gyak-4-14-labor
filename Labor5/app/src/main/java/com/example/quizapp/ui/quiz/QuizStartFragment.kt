package com.example.quizapp.ui.quiz

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.database.getStringOrNull
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
//import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.REQUEST_SELECT_CONTACT
import com.example.quizapp.shared.SharedViewModel

//import com.example.quizapp.models.QuizController
//import com.example.quizapp.models.QuizViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizStartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
//const val EXTRA_MESSAGE = "com.example.quizapp.MESSAGE"
class QuizStartFragment : Fragment() {
    private val TAG : String = "StartQuizFragment"
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var userName: EditText
    private lateinit var startButton: Button
    private lateinit var chooseContactButton: Button
    private val viewModel : SharedViewModel by activityViewModels()
    private lateinit var  contactUri : Uri



    // Capture the layout's TextView and set the string as its text


    /*
    @SuppressLint("Range")
    private val getPerson: registerForActivityResult(MainActivity.PickContact()){
        //userName.text = viewModel.playerName
    }
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_quiz_start, container, false)
        //BackButton()
        if (view != null)
        {
            initializeView(view)
            registerListeners()
        }


        return view
    }

    private fun initializeView(view: View)
    {
        view.apply {
            userName = view.findViewById(R.id.userName)
            startButton = view.findViewById(R.id.startButton)
            chooseContactButton = view.findViewById(R.id.chooseContactbtn)
        }
    }

    private fun registerListeners()
    {
        startButton.setOnClickListener { onStartButtonPressed() }
        val getContent = registerForActivityResult(
            ActivityResultContracts.PickContact(),
            ActivityResultCallback {
                val names = listOf(ContactsContract.Contacts.DISPLAY_NAME).toTypedArray()

                val cursor = requireActivity().contentResolver.query(it!!, names, null, null, null)
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        this.userName.setText(cursor.getString(0))
                        //viewModel.changePlayerName(this.userName.text.toString())
                    }
                    cursor.close()
                }
            })
        chooseContactButton.setOnClickListener()
        {

            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE

            getContent.launch(null)



        }


    }


    fun onStartButtonPressed()
    {
        findNavController().navigate(R.id.action_quizStartFragment_to_questionFragment)
    }

    private fun getContactName() {
        val cursor = requireActivity().contentResolver.query(contactUri!!,null,null,null,null)
        //
        if(cursor!!.moveToFirst()){
            val contactName = cursor.getStringOrNull(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            userName.setText(contactName)

        }
        cursor.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_SELECT_CONTACT && resultCode == Activity.RESULT_OK)
        {
            contactUri = data!!.data!!
            getContactName()
        }
    }







    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuizStartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuizStartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }





}