package com.example.quizapp

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button

import android.content.Intent
import android.net.Uri
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView


import android.provider.ContactsContract
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle


import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.*
import androidx.navigation.findNavController
import com.example.quizapp.Retrofit.RetrofitInterface
import com.example.quizapp.models.QuestionModel
//import com.example.quizapp.interfaces.DrawerLocker
import com.example.quizapp.shared.SharedViewModel
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val TAG_MAIN : String =  "MainActivity"
const val REQUEST_SELECT_CONTACT = 1



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var startButton : Button
    private lateinit var chooseContactButton : Button
    private lateinit var userName : TextView
    private lateinit var contactUri : Uri
    private lateinit var toolbar: Toolbar
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var fragmentContainerView : FragmentContainerView
    val model : SharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG_MAIN, "onCreate() called")
        setContentView(R.layout.activity_main)

        val rf = Retrofit.Builder()
            .baseUrl(RetrofitInterface.Base_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val API = rf.create(RetrofitInterface::class.java)
        val call = API.posts
        call?.enqueue(object : Callback<List<QuestionModel?>?>
        {
            override fun onResponse(
                call: Call<List<QuestionModel?>?>,
                response: Response<List<QuestionModel?>?>
            ) {
                val postList : List<QuestionModel>? = response.body() as List<QuestionModel>
                val post = arrayOfNulls<String>(postList!!.size)
                for (i in postList!!.indices)
                {
                    post[i] = postList!![i]!!.question
                    model.addQuestion(postList!![i]!!.question, postList!![i]!!.answers!!)
                }


            }

            override fun onFailure(call: Call<List<QuestionModel?>?>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to load questions!", Toast.LENGTH_SHORT).show()

            }
        })





        //val navController = findNavController(R.id.nav_host_fragment)
        //NavigationUI.setupActionBarWithNavController(this, navController)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )


        drawer.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        navigationView = findViewById(R.id.navigationViewId)

        navigationView.setNavigationItemSelectedListener(this)
        fragmentContainerView = findViewById(R.id.nav_host_fragment)





    }


    //azert kell hogy a kerdeseknel ne lehessen elerni a menut

    fun setDrawerLocked(shouldLock: Boolean) {
        if(shouldLock)
        {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }else
        {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }



    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        toggle.syncState()
        super.onPostCreate(savedInstanceState, persistentState)
    }

    //ez azert kell hogyha meg van nyitva a sidemenu akkor lepjen ki egyszer abbol
    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START)
        }else
        {
            super.onBackPressed()
        }

    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val fragment = fragmentContainerView.getFragment<Fragment>()


        when(item.itemId)
        {
            R.id.home_menuBtn ->
            {
                findNavController(fragment.id).navigate(R.id.homeFragment)

            }
            R.id.profile_menuBtn ->
            {
                findNavController(fragment.id).navigate(R.id.profileFragment)

            }
            R.id.quizTime_menuBtn ->
            {
                findNavController(fragment.id).navigate(R.id.quizStartFragment)
            }
            R.id.listOfQuestions_menuBtn ->
            {
                findNavController(fragment.id).navigate(R.id.questionListFragment)
            }
            R.id.newQuestion_menuBtn ->
            {
                findNavController(fragment.id).navigate(R.id.questionAddragment)
            }

        }
        drawer.closeDrawer(GravityCompat.START)


        return true

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


    //it annoyed me that the keyboard stayed after the button press, so I solved it (stackoverflow function)
    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }







}
