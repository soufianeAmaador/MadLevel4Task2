package com.example.madlevel4task2v20

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        setSupportActionBar(toolbar)
        navController = findNavController(R.id.nav_host_fragment)


    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        navController.addOnDestinationChangedListener { _,       destination, _ ->
            if (destination.id in arrayOf(R.id.historyFragment)) {
                toolbar.title = "Your Game History"
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar.menu.findItem(R.id.action_history).isVisible = false
                toolbar.menu.findItem(R.id.action_delete)?.isVisible = true


            } else if (destination.id in arrayOf(R.id.gameFragment)){
                toolbar.title = "Mad Level 4 Task 2"
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                toolbar.menu.findItem(R.id.action_history)?.isVisible = true
                toolbar.menu.findItem(R.id.action_delete)?.isVisible = false

            }
        }
        return super.onPrepareOptionsMenu(menu)

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        val idBackButton = 16908332;

         when (item.itemId) {
            R.id.action_history -> {
                navController.navigate(R.id.action_gameFragment_to_historyFragment)
                true
            }
             idBackButton -> {
//                 navController.navigate(R.id.action_historyFragment_to_gameFragment)
                 navController.popBackStack()
                 true
             }
             R.id.action_delete -> {
                return false
             }

            else -> super.onOptionsItemSelected(item)

        }
        invalidateOptionsMenu()
        return true

    }

}