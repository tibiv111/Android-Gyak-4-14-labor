package com.example.quizapp.ui.quiz

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.database.getStringOrNull
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.quizapp.MainActivity
//import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.REQUEST_SELECT_CONTACT
import com.example.quizapp.shared.SharedViewModel
import java.lang.ref.WeakReference
import kotlin.coroutines.coroutineContext

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
    private val TAG: String = "StartQuizFragment"
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var userName: EditText
    private lateinit var startButton: Button
    private lateinit var chooseContactButton: Button
    lateinit var progressBar: ProgressBar
    private lateinit var viewModel: SharedViewModel
    private lateinit var contactUri: Uri
    var myVariable = 10


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz_start, container, false)
        //BackButton()
        if (view != null) {
            initializeView(view)
            registerListeners()
        }


        return view
    }

    private fun initializeView(view: View) {
        view.apply {

            userName = view.findViewById(R.id.userName)
            startButton = view.findViewById(R.id.startButton)
            chooseContactButton = view.findViewById(R.id.chooseContactbtn)
            progressBar = view.findViewById(R.id.progressBar)
        }
    }

    private fun registerListeners() {
        startButton.setOnClickListener { onStartButtonPressed() }
        val getContent = registerForActivityResult(
            ActivityResultContracts.PickContact(),
            ActivityResultCallback {
                val names = listOf(ContactsContract.Contacts.DISPLAY_NAME).toTypedArray()

                val cursor = requireActivity().contentResolver.query(it!!, names, null, null, null)
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        this.userName.setText(cursor.getString(0))
                        //viewModel.changePlayerName(userName.text)
                    }
                    cursor.close()
                }

            })



        chooseContactButton.setOnClickListener()
        {

            Log.d("contactTAG", "chooseContactButton starts")
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE

            getContent.launch(null)
            Log.d("contactTAG", "chooseContactButton ends")

        }


    }


    fun onStartButtonPressed() {
        val task = loadQuestionsAsyncTask(this)
        task.execute(1)


    }

    private fun getContactName() {
        Log.d("contactTAG", "getContactName starts")
        val cursor = requireActivity().contentResolver.query(contactUri, null, null, null, null)
        //
        if (cursor!!.moveToFirst()) {
            val contactName =
                cursor.getStringOrNull(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            userName.setText(contactName)

        }
        cursor.close()
        Log.d("contactTAG", "getContactName ends")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("contactTAG", "onActivityResult starts")
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == Activity.RESULT_OK) {
            contactUri = data!!.data!!
            getContactName()
        }
        Log.d("contactTAG", "onActivityResult ends")
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


        class loadQuestionsAsyncTask internal constructor(context: QuizStartFragment) :
            AsyncTask<Int, String, String?>() {
            private var resp: String? = null
            private val fragmentReference: WeakReference<QuizStartFragment> = WeakReference(context)
            private val viewModel: SharedViewModel by fragmentReference.get()!!.activityViewModels()


            override fun onPreExecute() {
                val fragment = fragmentReference.get()
                if (fragment == null || fragment.isRemoving) return
                {
                    fragment.progressBar.visibility = View.VISIBLE
                }



            }

            override fun doInBackground(vararg params: Int?): String? {
                publishProgress("Loading questions") // Calls onProgressUpdate()
                try {
                    viewModel.loadQuestions()
                    val time = params[0]?.times(1000)
                    time?.toLong()?.let { Thread.sleep(it) }
                    Log.i("logTest", "Here is the async function")
                    publishProgress("Good luck!") // Calls onProgressUpdate()
                    resp = "Loading finished"

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                    resp = e.message
                } catch (e: Exception) {
                    e.printStackTrace()
                    resp = e.message
                }

                return resp
            }
            override fun onPostExecute(result: String?) {

                //val activity = fragmentReference.get()
                //if (activity == null || activity.isRemoving) return

                val fragment = fragmentReference.get()
                if (fragment == null || fragment.isRemoving) return
                fragment.progressBar.visibility = View.GONE
                fragment.myVariable = 100
                findNavController(fragment).navigate(R.id.action_quizStartFragment_to_questionFragment)
            }

            override fun onProgressUpdate(vararg text: String?) {

                val fragment = fragmentReference.get()
                if (fragment == null || fragment.isRemoving) return

                Toast.makeText(fragment.context, text.firstOrNull(), Toast.LENGTH_SHORT).show()


            }




        }


    }

}