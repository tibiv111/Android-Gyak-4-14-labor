package com.example.quizapp

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView


import android.provider.ContactsContract
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels


import androidx.core.database.getStringOrNull
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.quizapp.shared.SharedViewModel


const val TAG_MAIN : String =  "MainActivity"
const val REQUEST_SELECT_CONTACT = 1



class MainActivity : AppCompatActivity() {

    private lateinit var startButton : Button
    private lateinit var chooseContactButton : Button
    private lateinit var userName : TextView
    private lateinit var  contactUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG_MAIN, "onCreate() called")
        setContentView(R.layout.activity_main)
        val model : SharedViewModel by viewModels()
        //val navController = findNavController(R.id.nav_host_fragment)
        //NavigationUI.setupActionBarWithNavController(this, navController)

    }



    class PickContact : ActivityResultContract<Int, Uri?>()
    {
        override fun createIntent(context: Context, input: Int): Intent =
            Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI).also {
                it.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            }


        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return if (resultCode == RESULT_OK) intent?.data else null
        }

    }
    private fun getContactName() {
        val cursor = contentResolver.query(contactUri!!,null,null,null,null)
        if(cursor!!.moveToFirst()){
            val contactName = cursor.getStringOrNull(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            userName.setText(contactName)

        }
    }















    //it annoyed me that the keyboard stayed after the button press, so I solved it (stackoverflow function)
    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }







}
