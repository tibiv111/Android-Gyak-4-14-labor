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


import androidx.core.database.getStringOrNull
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit


const val EXTRA_MESSAGE = "com.example.quizapp.MESSAGE"
const val TAG_MAIN : String =  "MainActivity"
const val REQUEST_SELECT_CONTACT = 1



class MainActivity : AppCompatActivity(R.layout.fragment_quiz_start) {

    private lateinit var startButton : Button
    private lateinit var chooseContactButton : Button
    private lateinit var userName : TextView
    private lateinit var  contactUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerListeners()
        initializeView()
        if (savedInstanceState == null) {
            val bundle = savedInstanceState
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<QuizStartFragment>(R.id.fragment_container_view, args = bundle)
            }
        }
    }


    fun sendMessage(view: View) {
        val editText = findViewById<EditText>(R.id.userName)
        val message = editText.text.toString()
        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }
    private fun registerListeners()
    {

        chooseContactButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = ContactsContract.Contacts.CONTENT_TYPE

            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, REQUEST_SELECT_CONTACT)

            }


        }



    }
    private fun initializeView()
    {

        userName = findViewById<TextView>(R.id.userName)
        startButton = findViewById<Button>(R.id.startButton)
        chooseContactButton = findViewById<Button>(R.id.chooseContactbtn)




    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_SELECT_CONTACT && resultCode == Activity.RESULT_OK)
        {
            contactUri = data!!.data!!
            getContactName()
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
